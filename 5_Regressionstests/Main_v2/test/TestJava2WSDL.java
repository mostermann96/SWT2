import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;


public class TestJava2WSDL {

    static final ConsoleRunner runner = new ConsoleRunner(Arrays.asList("java","org.apache.axis.wsdl.Java2WSDL"));

    /**
     * Stellt sicher, dass die Tool-Aufrufe erfolgreich sind
     *
     * @param succeeded Rückgabewert von runJava2WSDL
     */
    private void requireSuccess(boolean[] succeeded) {
        assertTrue(succeeded[0]);
        assertTrue(succeeded[1]);
    }

    @BeforeClass
    public static void cleanOutDir(){
        File[] files = new File(runner.getOutputDir()).listFiles(new CustomFilenameFilter());
        if (files != null) {
            for (File f : files)
                f.delete();
        }
    }

    /**
     *  So kann der Runner benutzt werden
     *  Wichtig: Ausgabedateinamen:
     *      .wsdl - hängt immer von der Eingabeklasse ab
     *      .txt - bei Umleitung der Konsolenausgabe nach Datei:
     *          wenn Eingabeklasse angegeben, dann immer deren Name
     *          ansonsten von options abgeleitet
     *          ansonsten "no_input"
     *
     *      siehe ConsoleRunner.getOutputFilenames und .getConsoleOutputFilenames
     */
    public void exampleRunnerCalls(){
        runner.runJava2WSDL("javax.xml.messaging.URLEndpoint", Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice"), true);
        // schreibt gleiche wsdl wie Zeile bevor
        runner.runJava2WSDL("javax.xml.messaging.URLEndpoint", null, true);
        runner.runJava2WSDL(null, Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice"), true);
        runner.runJava2WSDL(null, null, true);
        // schreibt gleiche txt wie Zeile bevor
        runner.runJava2WSDL(null, null);
        runner.runJava2WSDL(null, Arrays.asList("--help"), true);
        // schreibt gleiche txt wie Zeile bevor
        runner.runJava2WSDL(null, Arrays.asList("--help"));
    }

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     * java org.apache.axis.wsdl.Java2WSDL -o test_output/WidgetPrice.orig.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
     * java org.apache.axis.wsdl.Java2WSDL -o test_output/WidgetPrice.mod.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
     */
    @Test
    public void testCorrectOutputFilesURLEndpointWidget() {
        String inClassName = "javax.xml.messaging.URLEndpoint";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice")));
        String outPath0 = runner.getOutputDir() + File.separator + runner.getOutputFilenames(inClassName)[0];
        String outPath1 = runner.getOutputDir() + File.separator + runner.getOutputFilenames(inClassName)[1];
        assertTrue(new File(outPath0).exists());
        assertTrue(new File(outPath1).exists());
    }

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     * java org.apache.axis.wsdl.Java2WSDL > test_output/help_orig.txt"
     * java org.apache.axis.wsdl.Java2WSDL > test_output/help_mod.txt"
     */
    /*
    @Test
    public void testCorrectOutputFilesHelp() {
        //String inPath = java2WSDLRunner.getInputDir() + File.separator + "Help.java";
        String fileExtension = "txt";
        String outPutCommand = ">";

        List<String> forOutput = Arrays.asList("help", fileExtension, outPutCommand);
        requireSuccess(runner.runJava2WSDL(forOutput,"", Collections.emptyList()));
        String outPath0 = runner.getOutputDir() + File.separator + runner.getOutputFilenames(forOutput)[0];
        String outPath1 = runner.getOutputDir() + File.separator + runner.getOutputFilenames(forOutput)[1];

        assertTrue(new File(outPath0).exists());
        assertTrue(new File(outPath1).exists());
    }
    */
}
