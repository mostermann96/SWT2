import java.io.File;
import java.io.FileNotFoundException;
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

    private static final String[] endingWsdl = new String[]{".orig.wsdl", ".mod.wsdl"};
    private static final String[] endingWsdlImpl = new String[]{".orig.wsdlimpl", ".mod.wsdlimpl"};
    private static final String[] endingTxt = new String[]{".orig.txt", ".mod.txt"};

    private static final String optionOut = "-o";
    private static final String optionOutImpl = "-O";

    private String[] wsdlOut;
    private String[] wsdlImplOut;
    private String[] consoleOut;

    public ConsoleRunner(List<String> cmdBase) {
        this.cmdBase = cmdBase;
        this.cmdOrig = new ArrayList<>(this.cmdBase);
        this.cmdMod = new ArrayList<>(this.cmdBase);
    }

    /**
     * Gibt Wsdl Ausgabedateipfade zurück
     */
    public String[] getWsdlOut() {
        return wsdlOut;
    }

    /**
     * Gibt Wsdl-Impl Ausgabedateipfade zurück
     */
    public String[] getWsdlImplOut() {
        return wsdlImplOut;
    }

    /**
     * Gibt Konsolen-Ausgabedateipfade zurück
     */
    public String[] getConsoleOut(){
        return consoleOut;
    }

    /**
     * Gibt den Pfad zur Datei zurück, die von der modifizierten Version ausgegeben wurde
     * @return relativen Pfad zur Ausgabedatei, null wenn nichts gefunden
     */
    public String findModWsdlOut() {
        if (wsdlOut != null) {
            String path = wsdlOut[1];
            return findModWsdlOut(path);
        }
        throw new IllegalArgumentException("wsdlOut ist nicht gesetzt, benutze findModWsdlOut(String)");
    }

    /**
     * Wie oben
     * Kann bei inferOutFilenames = false (siehe runJava2WSDL) benutzt werden, um Ausgabedatei zu finden
     */
    public String findModWsdlOut(String customOutFilename) {
        if (!customOutFilename.contains(".")){
            throw new IllegalArgumentException("Java2WSDL sollte zuvor schon eine Exception geworfen haben");
        }

        String out = customOutFilename.substring(0, customOutFilename.indexOf("."));
        out += "_impl.wsdl";

        if (!(new File(out).exists())) {
            System.err.println("Modifizierte Axis sollte " + out + " geschrieben haben, aber Datei existiert nicht");
            return null;
        }
        return out;
    }

    /**
     * für Kompatibilität - nicht weiter verwenden
     */
    public String findOutputFile(){
        return findModWsdlOut();
    }

    /**
     * Nicht weiter benutzen
     * Gibt Wsdl Ausgabedateipfade zurück - für Kompatibilität
     */
    public String[] getOutputFilenames(String classString) {
        return wsdlOut;
    }

    /**
     * Nicht weiter benutzen
     * Gibt Konsolen-Ausgabedateipfade zurück - für Kompatibilität
     */
    public String[] getConsoleOutputFilenames(String inClassName, List<String> options){
        return consoleOut;
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

    /**
     * Ruft runJava2WSDL auf mit stdoutToFile = false, inferOutFilenames = true
     */
    public int[] runJava2WSDL(String inClassName, List<String> options) {
        return runJava2WSDL(inClassName, options, false, true);
    }

    /**
     * Ruft das Tool 2-Mal auf, einmal mit original axis.jar, einmal mit modifizierter axis.jar
     *
     * @param inClassName Name der Eingabeklasse (mit Package), null für keine Eingabeklasse
     * @param options     Optionen für Java2WSDL
     * @param stdoutToFile true -> leite Konsolenausgabe nach Datei um
     * @param inferOutFilenames true -> Dateinamen werden generiert basierend auf angegebenen Dateinamen
     * @return bool Array: [Original erfolgreich ausgeführt, Modifierte Version erfolgreich ausgeführt]
     */
    public int[] runJava2WSDL(String inClassName, List<String> options, boolean stdoutToFile, boolean inferOutFilenames) {
        // Cmd zurücksetzen
        resetCommands();

        if (options == null)
            options = new LinkedList<>();
        else
            options = new LinkedList<>(options);

        // wsdl Ausgabedateien
        if (inClassName != null && inferOutFilenames) {
            setWSDLOutputFiles(inClassName, (LinkedList<String>) options);
            setWSDLImplOutputFiles(inClassName, (LinkedList<String>) options);
        }

        // restliche Optionen übernehmen
        cmdOrig.addAll(options);
        cmdMod.addAll(options);

        // Eingabeklasse
        if (inClassName != null){
            cmdOrig.add(inClassName);
            cmdMod.add(inClassName);
        }

        // Stdout-Dateien für Subprozesse
        File[] outFiles = new File[2];
        if (stdoutToFile) {
            consoleOut = generateConsoleOutputFilenames(inClassName, options);
            outFiles[0] = new File(consoleOut[0]);
            outFiles[1] = new File(consoleOut[1]);
        }
        else
            consoleOut = null;

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

    private String getBaseFilename(String classString) {
        return classString.substring(classString.lastIndexOf(".") + 1);
    }

    private String[] generateConsoleOutputFilenames(String inClassName, List<String> options){
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
        return new String[]{base + endingTxt[0], base + endingTxt[1]};
    }

    private String removeOption(LinkedList<String> options, String option){
        int i = options.indexOf(option);
        if (i == -1){
            return null;
        }
        else{
            options.remove(i);
            return options.remove(i);
        }
    }

    private String[] setOutputFiles(String option, String inClassName, String base, String[] ending){
        String filename = base;
        if (filename.equals(""))
            filename = getBaseFilename(inClassName);

        if (!base.startsWith(outputDir))
            filename = outputDir + File.separator + filename;

        if (filename.contains("."))
            filename = filename.substring(0, filename.indexOf("."));

        String[] filenames = new String[]{filename + ending[0], filename + ending[1]};
        cmdOrig.addAll(Arrays.asList(option, filenames[0]));
        cmdMod.addAll(Arrays.asList(option, filenames[1]));

        return filenames;
    }

    private void setWSDLOutputFiles(String inClassName, LinkedList<String> options){
        String out = removeOption(options, optionOut);
        if (out == null || out.equals(""))
            out = getBaseFilename(inClassName);
        wsdlOut = setOutputFiles(optionOut, inClassName, out, endingWsdl);
    }

    private void setWSDLImplOutputFiles(String inClassName, LinkedList<String> options){
        String out = removeOption(options, optionOutImpl);
        if (out != null){
            wsdlImplOut = setOutputFiles(optionOutImpl, inClassName, out, endingWsdlImpl);
        }
        else
            wsdlImplOut = null;
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
