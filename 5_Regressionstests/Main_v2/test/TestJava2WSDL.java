import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
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
        assertEquals("Original: Ausführung nicht erfolgreich", 0, retVals[0]);
        assertEquals("Modifiziert: Ausführung nicht erfolgreich", 0, retVals[1]);
    }

    /**
     * Testet Gleichheit der Exit-Codes der Java2WSDL Aufrufe
     * @param retVals Rückgabewert von runJava2WSDL
     */
    private void requireEqualExitCodes(int[] retVals){
        assertEquals("Exit-Codes der Java2WSDL-Aufrufe unterschiedlich", retVals[0], retVals[1]);
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
            fail("Zu lesende Datei ist nicht vorhanden");
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
        runner.runJava2WSDL("javax.xml.messaging.URLEndpoint", Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice"), true, true);
        // schreibt gleiche wsdl wie Zeile bevor
        runner.runJava2WSDL("javax.xml.messaging.URLEndpoint", null, true, true);
        runner.runJava2WSDL(null, Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice"), true, true);
        runner.runJava2WSDL(null, null, true, true);
        // schreibt gleiche txt wie Zeile bevor
        runner.runJava2WSDL(null, null);
        runner.runJava2WSDL(null, Arrays.asList("--help"), true, true);
        runner.runJava2WSDL(null, Arrays.asList("--help"));
    }

    /**
     * Java2WSDL-Aufrufe ohne Angabe einer Ausgabedatei
     */
    @Test
    public void testNoOutputFile(){
        String inClassName = "javax.xml.messaging.URLEndpoint";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation"), false, false));
    }

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     */
    @Test
    public void testCorrectOutputFiles() {
        String inClassName = "javax.xml.messaging.URLEndpoint";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice")));
        assertTrue("Original: Ausgabedatei nicht vorhanden", new File(runner.getWsdlOut()[0]).exists());
        assertTrue("Modifiziert: Ausgabedatei nicht vorhanden", new File(runner.getWsdlOut()[1]).exists());
    }

    /**
     * Übereinstimmung von Konsolen-Ausgabe bei Angabe von Option --help
     */
    @Test
    public void testOptionHelp(){
        List<String> options = Arrays.asList("--help");
        requireEqualExitCodes(runner.runJava2WSDL(null, options, true, true));
        String[] outFilenames = runner.getConsoleOut();
        assertEquals("Help Ausgabe unterscheidet sich", readFile(outFilenames[0]), readFile(outFilenames[1]));
    }

    /**
     * Übereinstimmung der WSDL-Ausgabe bei Eingabe mit Debug-Infos
     * Die Referenz gibt an, dass bei Eingabe einer Klasse, die mit allen Debug-Informationen kompiliert wurde,
     * die Methoden-Parameternamen ausgelesen und benutzt werden.
     * TODO robuster WSDL Vergleich (Vergleich schlägt schon wegen verschiedenen Erstellungs-Zeiten der Dateien im Header fehl)
     */
    @Test
    public void testRunWithDebugInfo(){
        String inClassName = "WidgetPriceDebug";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation")));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals("Ausgabe unterscheidet sich", readFile(runner.getWsdlOut()[0]), readFile(runner.findOutputFile()));
    }
}
