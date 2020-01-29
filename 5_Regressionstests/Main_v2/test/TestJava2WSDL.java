import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


public class TestJava2WSDL {

    static final ConsoleRunner runner = new ConsoleRunner(Arrays.asList("java","org.apache.axis.wsdl.Java2WSDL"));

    /**
     * Stellt sicher, dass die Java2WSDL Aufrufe erfolgreich sind
     * @param retVals Rückgabewert von runJava2WSDL
     */
    private void requireSuccess(int[] retVals) {
        assertEquals(0, retVals[0]);
        assertEquals(0, retVals[1]);
    }

    /**
     * Testet Gleichheit der Exit-Codes der Java2WSDL Aufrufe
     * @param retVals Rückgabewert von runJava2WSDL
     */
    private void requireEqualExitCodes(int[] retVals){
        assertEquals(retVals[0], retVals[1]);
    }

    /**
     * Liest Datei in einen String
     * @param path Pfad zu einer Text-Datei als String
     * @return Inhalt der Datei als String
     */
    private String readFile(String path){
        Scanner s = null;
        try{
            s = new Scanner(new File(path));
        } catch (FileNotFoundException e){
            return null;
        }
        return s.useDelimiter("\\Z").next();
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
     * Übereinstimmung von Konsolen-Ausgabe bei Angabe von Option --help
     */
    @Test
    public void testOptionHelp(){
        List<String> options = Arrays.asList("--help");
        requireEqualExitCodes(runner.runJava2WSDL(null, options, true));
        String[] outFilenames = runner.getConsoleOutputFilenames(null, options);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
    }

    /**
     * Übereinstimmung der WSDL-Ausgabe bei Eingabe mit Debug-Infos
     */
    @Test
    public void testRunWithDebugInfo(){
        String inClassName = "WidgetPriceDebug";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation")));
        String[] outFilenames = runner.getOutputFilenames(inClassName);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
    }

// ab hier 'neue' Testfälle von Flo

    /**
     * Übereinstimmung der Ausgabe bei Eingabe einer Input-WSDL Datei
     */
    @Test
    public void testOptionInput(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation",
                "-i", "test_input"+System.getProperty("file.separator")+"dummy_input.wsdl",
                "-o", "test_output"+System.getProperty("file.separator")+"dummy_output.wsdl")));
        String[] outFilenames = runner.getOutputFilenames(inClassName);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
        //TODO: testen, ob auch wirklich gleich danach
    }

    @Test
    public void testPortNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-s", "alteredPortName")));
        String[] outFilenames = runner.getOutputFilenames(inClassName);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
        //TODO: testen, ob wsdl portname auch wirklich alteredPortName
    }

    @Test
    public void testPortTypeNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation",
                "-P", "alteredTypeName")));
        String[] outFilenames = runner.getOutputFilenames(inClassName);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
        //TODO: testen, ob wsdl portTypeName auch wirklich alteredTypeName
    }

    @Test
    public void testBindingNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-s", "alteredPortName",
                "-b", "someBindingName")));
        String[] outFilenames = runner.getOutputFilenames(inClassName);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
        //TODO: testen, ob wsdl bindingName auch wirklich someBindingName
    }
}
