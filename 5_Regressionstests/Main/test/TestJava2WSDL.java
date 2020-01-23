import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class TestJava2WSDL {

    private static String inputDir = "test_input";
    private static String outputDir = "test_output";

    private static List<String> cmdBase = Arrays.asList("java", "org.apache.axis.wsdl.Java2WSDL");
    private static String cpOrig = "axis-1_4" + File.separator + "lib" + File.separator + "*" + File.pathSeparator + inputDir;
    private static String cpMod = "axis-modified.jar" + File.pathSeparator + "axis-1_4" + File.separator + "lib" + File.separator + "*" + File.pathSeparator + inputDir;

    private String getBaseFilename(String path) {
        String filename = Paths.get(path).getFileName().toString();
        return filename.substring(0, filename.lastIndexOf("."));
    }

    private String[] getOutputFilenames(String inPath) {
        String filename = getBaseFilename(inPath);
        return new String[]{filename + ".orig.wsdl", filename + ".mod.wsdl"};
    }

    private int runProcess(List<String> cmd, String cp){
        // in/out/err und Arbeitsverzeichnis von diesem Prozess übernehmen
        ProcessBuilder builder = new ProcessBuilder(cmd)
                .inheritIO()
                .directory(null);
        // classpath setzen
        Map<String, String> env = builder.environment();
        env.put("CLASSPATH", cp);
        // Ausführen
        Process process;
        try{
            process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e){
            System.err.println("Prozess konnte nicht ausgeführt werden:");
            System.err.println(cmd.toString());
            System.err.println(cp);
            System.err.println(e.toString());
            fail();     // Todo Review: möglicherweise soll hier nicht abgebrochen werden
            return 100;
        }
        return process.exitValue();
    }

    /**
     * Ruft das Tool 2-Mal auf, einmal mit original axis.jar, einmal mit modifizierter axis.jar
     * @param inPath Pfad zur Eingabedatei
     * @param inClassName Name der Eingabeklasse (wegen Package-Informationen)
     * @param options Optionen für Java2WSDL
     * @return bool Array: [Original erfolgreich ausgeführt, Modifierte Version erfolgreich ausgeführt]
     */
    private boolean[] runJava2WSDL(String inPath, String inClassName, List<String> options) {
        // Ausgabe-Namen für WSDL-Dateien
        String[] outFilenames = getOutputFilenames(inPath);

        // Befehle zusammensetzen
        ArrayList<String> cmdOrig = new ArrayList<>(cmdBase);
        // Optionen übernehmen
        cmdOrig.addAll(options);
        ArrayList<String> cmdMod = new ArrayList<>(cmdOrig);
        // Ausgabedateien
        cmdOrig.addAll(Arrays.asList("-o", outputDir + File.separator + outFilenames[0]));
        cmdMod.addAll(Arrays.asList("-o", outputDir + File.separator + outFilenames[1]));
        // Eingabeklassen
        cmdOrig.add(inClassName);
        cmdMod.add(inClassName);

        //System.out.println(cmdOrig.toString() + "\n" + cmdMod.toString());

        // Prozesse ausführen
        boolean[] retVal = new boolean[2];
        retVal[0] = runProcess(cmdOrig, cpOrig) == 0;
        retVal[1] = runProcess(cmdMod, cpMod) == 0;

        return retVal;
    }

    /**
     * Stellt sicher, dass die Tool-Aufrufe erfolgreich sind
     * @param succeeded Rückgabewert von runJava2WSDL
     */
    private void requireSuccess(boolean[] succeeded){
        assertTrue(succeeded[0]);
        assertTrue(succeeded[1]);
    }

    /**
     * Gibt den Pfad zur Datei zurück, die von der modifizierten Version ausgegeben wurde
     * @param inPath Pfad der Eingabedatei
     * @return relativen Pfad zur Ausgabedatei, null wenn nichts gefunden
     */
    private String findOutputFile(String inPath) {
        String base = getBaseFilename(inPath);
        for (File f : Objects.requireNonNull(new File(outputDir).listFiles(), "Ausgabeverzeichnis leer!")) {
            if (f.getPath().contains(base) && !f.getPath().contains("orig.wsdl"))
                return f.getPath();
        }
        return null;
    }

    @BeforeClass
    public static void cleanOutDir(){
        File[] files = new File(outputDir).listFiles(new CustomFilenameFilter());
        if (files != null) {
            for (File f : files)
                f.delete();
        }
    }

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     */
    @Test
    public void testCorrectOutputFiles() {
        String inPath = inputDir + File.separator + "WidgetPrice.java";
        requireSuccess(runJava2WSDL(inPath, "WidgetPrice", Arrays.asList("-l", "localhost:8000/hello")));
        String outPath0 = outputDir + File.separator + getOutputFilenames(inPath)[0];
        String outPath1 = outputDir + File.separator + getOutputFilenames(inPath)[1];
        assertTrue(new File(outPath0).exists());
        assertTrue(new File(outPath1).exists());
    }
}
