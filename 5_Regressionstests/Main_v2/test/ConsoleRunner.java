import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConsoleRunner {
    private final String inputDir = "test_input";
    private final String outputDir = "test_output";



    private final List<String> cmdBase;
    private final String cpOrig = "axis-1_4" + File.separator + "lib" + File.separator + "*";
    //private static String cpMod = "axis-modified.jar" + File.pathSeparator + "axis-1_4" + File.separator + "lib" + File.separator + "*" + File.pathSeparator + inputDir;
    private final String cpMod = "axis-1_4_modified" + File.separator + "lib" + File.separator + "*";
    private ArrayList<String> cmdOrig;
    private ArrayList<String> cmdMod;
    public ConsoleRunner(List<String> cmdBase) {
        this.cmdBase = cmdBase;
        this.cmdOrig=new ArrayList<>(this.cmdBase);
        this.cmdMod=this.cmdOrig;
    }
    public ArrayList<String> getCmdOrig() {
        return cmdOrig;
    }

    public ArrayList<String> getCmdMod() {
        return cmdMod;
    }



    public String getBaseFilename(String path) {
        String filename = Paths.get(path).getFileName().toString();
        return filename.substring(0, filename.lastIndexOf("."));
    }

    public String[] getOutputFilenames(List<String> forOutput) {
        //String filename = getBaseFilename(inPath);
        return new String[]{forOutput.get(0) + "_orig." + forOutput.get(1), forOutput.get(0) + "_mod." + forOutput.get(1)};
    }

    public static Process runCmdAsync(List<String> cmd) {
        try {
            return new ProcessBuilder(cmd).start();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public int runProcess(List<String> cmd, String cp) {
        // in/out/err und Arbeitsverzeichnis von diesem Prozess übernehmen
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process process;
        builder.environment().put("CLASSPATH", cp);
        if (cmd.contains(">")) {
            runProcessWithStdOut(cmd, builder);
        } else {
            builder.command(cmd).inheritIO();
        }
        try {

            //builder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(cmd.get(1))));
            process = builder.start();
            //OutputStream stdin = process.getOutputStream ();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Prozess konnte nicht ausgeführt werden:");
            System.err.println(cmd.toString());
            System.err.println(cp);
            System.err.println(e.toString());
            //  fail();     // Todo Review: möglicherweise soll hier nicht abgebrochen werden
            return 100;
        }
        process.destroy();
        return 0;
    }

    private void runProcessWithStdOut(List<String> cmd, ProcessBuilder builder) {
        ArrayList<String> cmd_new = new ArrayList<>();
        for (String i : cmd) {
            if (!i.equals(">") && !i.startsWith(outputDir)) {
                cmd_new.add(i);
            }
        }
        System.out.println(cmd_new + "cmd_new");
        System.out.println(cmd.toString().substring(0, cmd.toString().lastIndexOf(">")));
        builder.command(cmd_new).inheritIO();
        int last_elem = cmd.size() - 1;
        File output = new File(cmd.get(last_elem));
        builder.redirectOutput(output);
    }

    /**
     * Ruft das Tool 2-Mal auf, einmal mit original axis.jar, einmal mit modifizierter axis.jar
     *
     * @param inPath      Pfad zur Eingabedatei
     * @param inClassName Name der Eingabeklasse (wegen Package-Informationen)
     * @param options     Optionen für Java2WSDL
     * @return bool Array: [Original erfolgreich ausgeführt, Modifierte Version erfolgreich ausgeführt]
     */
    public boolean[] runJava2WSDL(List<String> forOutput, String inClassName, List<String> options) {

        // Optionen übernehmen
        setPathCmdOrig(options);
        setPathCmdMod(options);
        // Ausgabedateien
        setOutputPieceOfPaths(forOutput);

        // Eingabeklassen
        setSamePieceOfPaths(inClassName);

        System.out.println(getCmdOrig().toString() + "\n" + getCmdMod().toString());

        // Prozesse ausführen
        System.out.println(cmdOrig);
        System.out.println(cmdMod);
        System.out.println(cpOrig);
        System.out.println(cpMod);
        boolean[] retVal = new boolean[2];
        retVal[0] = runProcess(getCmdOrig(), cpOrig) == 0;
        retVal[1] = runProcess(getCmdMod(), cpMod) == 0;
        System.out.println(cpOrig);
        System.out.println(cpMod);

        return retVal;
    }

    /**
     * Gibt den Pfad zur Datei zurück, die von der modifizierten Version ausgegeben wurde
     *
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

    public String getOutputDir() {
        return outputDir;
    }

    public String getInputDir() {
        return inputDir;
    }

    public List<String> getCmdBase() {
        return cmdBase;
    }

    public String getCpOrig() {
        return cpOrig;
    }

    public String getCpMod() {
        return cpMod;
    }

    //to set cmdOrigin path
    public void setPathCmdOrig(List<String> options) {
        if (!options.isEmpty()) {
            cmdOrig.addAll(options);
        }
    }

    //to set cmdMod path
    public void setPathCmdMod(List<String> options) {
        if (!options.isEmpty()) {
            cmdMod.addAll(options);
        }
    }

    public void setOutputPieceOfPaths(List<String> forOutput) {
        if (!forOutput.get(0).isEmpty() && !forOutput.get(1).isEmpty() && !forOutput.get(2).isEmpty()) {
            // Ausgabe-Namen für WSDL-Dateien
            String[] outFilenames = getOutputFilenames(forOutput);


            setPathCmdOrig(Arrays.asList(forOutput.get(2), outputDir + File.separator + outFilenames[0]));
            setPathCmdMod(Arrays.asList(forOutput.get(2), outputDir + File.separator + outFilenames[1]));
        }

    }

    public void setSamePieceOfPaths(String inClassName) {
        if (!inClassName.isEmpty()) {
            // Ausgabe-Namen für WSDL-Dateien
            setPathCmdOrig(Arrays.asList(inClassName));
            setPathCmdMod(Arrays.asList(inClassName));
        }
    }

    public void clearPaths() {
        cmdMod.clear();
        cmdMod.addAll(cmdBase);
        cmdOrig.clear();
        cmdOrig.addAll(cmdBase);
    }

}
