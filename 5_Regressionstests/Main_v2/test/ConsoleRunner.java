import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleRunner {
    private static final String inputDir = "test_input";
    private static final String outputDir = "test_output";

    private final List<String> cmdBase;

    private final String cpOrig = "axis-1_4" + File.separator + "lib" + File.separator + "*" + File.pathSeparator + inputDir;
    private static String cpMod = "axis-modified.jar" + File.pathSeparator + "axis-1_4" + File.separator + "lib" + File.separator + "*" + File.pathSeparator + inputDir;
    //private final String cpMod = "axis-1_4_modified" + File.separator + "lib" + File.separator + "*";

    private ArrayList<String> cmdOrig;
    private ArrayList<String> cmdMod;

    public ConsoleRunner(List<String> cmdBase) {
        this.cmdBase = cmdBase;
        this.cmdOrig = new ArrayList<>(this.cmdBase);
        this.cmdMod = new ArrayList<>(this.cmdBase);
    }

    private String getBaseFilename(String classString) {
        return classString.substring(classString.lastIndexOf(".") + 1);
    }

    public String[] getOutputFilenames(String classString) {
        String base = outputDir + File.separator + getBaseFilename(classString);
        return new String[]{base + ".orig.wsdl", base + ".mod.wsdl"};
    }

    public String[] getConsoleOutputFilenames(String inClassName, List<String> options){
        String base = outputDir + File.separator;
        if (inClassName != null)
            base += getBaseFilename(inClassName);
        else {
            if (options != null) {
                // Optionen werden als Dateiname verwendet, daher nur sichere Zeichen zulassen
                String optionStr = options.toString().replaceAll("[^-,\\w]", "");
                base += optionStr;
            }
            else
                base += "no_input";
        }
        return new String[]{base + ".orig.txt", base + ".mod.txt"};
    }

    public static Process runCmdAsync(List<String> cmd) {
        try {
            return new ProcessBuilder(cmd).start();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public int runProcess(List<String> cmd, String cp, File outFile) {
        // in/out/err und Arbeitsverzeichnis von diesem Prozess übernehmen
        ProcessBuilder builder = new ProcessBuilder(cmd)
                .inheritIO();
        // classpath setzen
        builder.environment().put("CLASSPATH", cp);

        // Ausgabedatei setzen
        if (outFile != null){
            builder.redirectOutput(outFile);
            builder.redirectError(outFile);
        }

        // Prozess ausführen
        Process process;
        try {
            process = builder.start();
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
        return process.exitValue();
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
     * Ruft runJava2WSDL mit stdoutToFile = false auf
     */
    public int[] runJava2WSDL(String inClassName, List<String> options) {
        return runJava2WSDL(true,inClassName, options, false, true);
    }

    /**
     * Ruft das Tool 2-Mal auf, einmal mit original axis.jar, einmal mit modifizierter axis.jar
     *
     * @param inClassName Name der Eingabeklasse (mit Package), null für keine Eingabeklasse
     * @param options     Optionen für Java2WSDL
     * @return bool Array: [Original erfolgreich ausgeführt, Modifierte Version erfolgreich ausgeführt]
     * so es gibt ein Unterschied, ob wir -o oder -O benutzen!!!
     */
    public int[] runJava2WSDL(boolean inClassNameAtTheEnd, String inClassName, List<String> options, boolean stdoutToFile, boolean oSmall) {

        // Cmd zurücksetzen
        resetCommands();

        // Optionen übernehmen
        if (options != null) {
            cmdOrig.addAll(options);
            cmdMod.addAll(options);
        }

        //Wir Eintscheiden, welche o benutzen wir
        String o="-o";
        if(!oSmall){
            o="-O";
        }

        // Ausggabedateien und Eingabeklasse
        if (inClassName != null&&inClassNameAtTheEnd){

            String[] outFilenames = getOutputFilenames(inClassName+getEndOfFileName(options,o));
            cmdOrig.addAll(Arrays.asList(o, outFilenames[0]));
            cmdMod.addAll(Arrays.asList(o, outFilenames[1]));

            cmdOrig.add(inClassName);
            cmdMod.add(inClassName);
        }
        else if(!inClassNameAtTheEnd){
            String[] outFilenames = getOutputFilenames(inClassName+getEndOfFileName(options,o));
            cmdOrig.addAll(Arrays.asList(o, outFilenames[0]));
            cmdMod.addAll(Arrays.asList(o, outFilenames[1]));
        }

        // Stdout-Dateien für Subprozesse
        File[] outFiles = new File[2];
        if (stdoutToFile) {
            String[] filenames = getConsoleOutputFilenames(inClassName, options);
            outFiles[0] = new File(filenames[0]);
            outFiles[1] = new File(filenames[1]);
        }


        // Prozesse ausführen
        System.out.println(cmdOrig);
        System.out.println(cmdMod);
//        System.out.println(cpOrig);
//        System.out.println(cpMod);
        int[] retVal = new int[2];
        retVal[0] = runProcess(cmdOrig, cpOrig, outFiles[0]);
        retVal[1] = runProcess(cmdMod, cpMod, outFiles[1]);

        return retVal;
    }

    public String getEndOfFileName(List<String> options, String o){
        String end_of_file_name="";

        for(String i:options){
            if (i.startsWith("-")){
                end_of_file_name=end_of_file_name+i;
            }
        }
        return end_of_file_name+o;
    }

    /**
     * Gibt den Pfad zur Datei zurück, die von der modifizierten Version ausgegeben wurde
     * @param inClass Name der Eingabeklasse, wie bei runJava2WSDL
     * @return relativen Pfad zur Ausgabedatei, null wenn nichts gefunden
     */
    public String findOutputFile(String inClass) {
        String base = getBaseFilename(inClass);
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

    public ArrayList<String> getCmdOrig() {
        return cmdOrig;
    }

    public ArrayList<String> getCmdMod() {
        return cmdMod;
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
            //String[] outFilenames = getOutputFilenames(forOutput);


            //setPathCmdOrig(Arrays.asList(forOutput.get(2), outputDir + File.separator + outFilenames[0]));
            //setPathCmdMod(Arrays.asList(forOutput.get(2), outputDir + File.separator + outFilenames[1]));
        }

    }

    public void setSamePieceOfPaths(String inClassName) {
        if (!inClassName.isEmpty()) {
            // Ausgabe-Namen für WSDL-Dateien
            setPathCmdOrig(Arrays.asList(inClassName));
            setPathCmdMod(Arrays.asList(inClassName));
        }
    }

    public void resetCommands() {
        cmdMod.clear();
        cmdMod.addAll(cmdBase);
        cmdOrig.clear();
        cmdOrig.addAll(cmdBase);
    }

}
