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

    private String getTag(String inputFile, String tag){
        return getTag(inputFile, tag, "");
    }

    private String getTag(String inputFile, String tag, String name){
        //TODO: implement this method
        return "";
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

// ab hier 'neue' Testfälle von Flo

    @Test
    public void testLocationOption(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName")));
        assertEquals("-l hat Name der SOAP Adresse nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdlsoap:address"),
                getTag(runner.findOutputFile(), "wsdlsoap:address"));
        assertEquals("-l hat Name des Service nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:service", inClassName),
                getTag(runner.findOutputFile(), "wsdl:service", inClassName));
    }

    /**
     * Übereinstimmung der Ausgabe bei Eingabe einer Input-WSDL Datei
     */
    @Test
    public void testOptionInput(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation",
                "-I", "test_input"+File.separator+"dummy_input.wsdl")));
        assertEquals("-I hat nicht richtig importiert",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "dummy"),
                getTag(runner.findOutputFile(), "wsdl:message", "dummy"));
        assertEquals("-I hat nicht richtig importiert",
                getTag(runner.getWsdlOut()[0], "wsdl:service"),
                getTag(runner.findOutputFile(), "wsdl:service"));
    }

    @Test
    public void testPortNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-s", "alteredPortName")));
        assertEquals("-s hat portname nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:port"), getTag(runner.findOutputFile(), "wsdl:port"));
    }

    @Test
    public void testPortTypeNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation",
                "-P", "alteredTypeName")));
        assertEquals("-P hat portTypeName nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:portType", "alteredTypeName"),
                getTag(runner.findOutputFile(), "wsdl:portType", "alteredTypeName"));
    }

    @Test
    public void testBindingNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-s", "alteredPortName",
                "-b", "someBindingName")));
        assertEquals("-b hat Name des Bindings nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:binding", "someBindingName"),
                getTag(runner.findOutputFile(), "wsdl:binding", "someBindingName"));
    }

    @Test
    public void testServiceElementNameOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-S", "alteredServiceElementName")));
        assertEquals("-S hat Name des Services nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:service", "alteredServiceElementName"),
                getTag(runner.findOutputFile(), "wsdl:service", "alteredServiceElementName"));
    }

    @Test
    public void testTargetNameSpaceOverwrite(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-n", "http://example.org")));
        assertEquals("-n hat Name des targetNamespace nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //TODO: evtl. muss hier mehr Logik angewendet werden falls andere Teile der wsdl:definitions auch anders ist
        //TODO: es sollte ja nur der targetNamespace überprüft werden
    }

    @Test
    public void testMethodsOnce(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-m", "setMethod1")));
        assertEquals("-m hat Methode nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "setMethod1Response"),
                getTag(runner.findOutputFile(), "wsdl:message", "setMethod1Response"));
        assertEquals("-m hat Methode nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "setMethod1Request"),
                getTag(runner.findOutputFile(), "wsdl:message", "setMethod1Request"));
        //TODO: außerdem zu testen: ist Anzahl der wsdl:message tags gleich?
        //TODO: sollte mit getTag(wsdl:definitions) machbar sein; danach die Anzahl zählen
    }

    @Test
    public void testMethodsTwice(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-m", "setMethod1 getMethod1")));
        assertEquals("-m hat Methode nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "setMethod1Response"),
                getTag(runner.findOutputFile(), "wsdl:message", "setMethod1Response"));
        assertEquals("-m hat Methode nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "setMethod1Request"),
                getTag(runner.findOutputFile(), "wsdl:message", "setMethod1Request"));
        assertEquals("-m hat Methode nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "getMethod1Response"),
                getTag(runner.findOutputFile(), "wsdl:message", "getMethod1Response"));
        assertEquals("-m hat Methode nicht richtig geschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "getMethod1Request"),
                getTag(runner.findOutputFile(), "wsdl:message", "getMethod1Request"));
        //TODO: außerdem zu testen: ist Anzahl der wsdl:message tags gleich?
        //TODO: sollte mit getTag(wsdl:definitions) machbar sein; danach die Anzahl zählen
    }

    @Test
    public void testNonExistentMethod(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-m", "nonExistentMethod")));
        //TODO: außerdem zu testen: ist Anzahl der wsdl:message tags gleich?
        //Erwartung: sollte jeweils 0x da sein
    }

    @Test
    public void testClassExtension(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-a")));
        //TODO: zu testen: ist Anzahl der wsdl:message tags gleich?
        //Erwartung: sollte jeweils 6x da sein
    }

    @Test
    public void testOutputModeAll(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "All")));
        //TODO: zu testen: ist Anzahl der wsdl:message tags gleich?
        //Erwartung: sollte jeweils 4x da sein
        //TODO: zu testen: ist Anzahl der wsdl:operation tags gleich?
        assertEquals("-W \"All\" hat portType nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:portType", "alteredTypeName"),
                getTag(runner.findOutputFile(), "wsdl:portType", "alteredTypeName"));

    }

    @Test
    public void testOutputModeInterface(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "Interface")));
        //TODO: zu testen: ist Anzahl der wsdl:message tags gleich?
        //Erwartung: sollte jeweils 4x da sein
        assertEquals("-W \"All\" hat portType nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:portType", "alteredTypeName"),
                getTag(runner.findOutputFile(), "wsdl:portType", "alteredTypeName"));
        //TODO: zu testen: ist Anzahl der wsdl:service tags gleich? -> sollte 0 sein
        //TODO: zu testen: ist Anzahl der wsdl:operation tags gleich?
    }

    @Test
    public void testOutputModeImplementation(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "Implementation",
                "-L", "test_input/dummy_input.wsdl")));
        assertEquals("-W \"All\" hat portType nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:import"),
                getTag(runner.findOutputFile(), "wsdl:import"));
    }

    @Test
    public void testImplementationNameSpace(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "All",
                "-N", "http://example.org")));
        assertEquals("-W \"All\" hat portType nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //TODO: wieder definitions noch filtern, um nur xmlns:impl zu testen
    }

    @Test
    public void testOutputImplName(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "Interface",
                "-O", "test_output/testOutputImplName.wsdlimpl")));
        //TODO: hier zu testen:
        //TODO: - name der Interface Dateien
        //TODO: - name der Implementation Dateien
    }

    @Test
    public void testInputImplClass(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-i", "test_input"+File.separator+"dummy_input.wsdlimpl")));
        //TODO: herausfinden, warum das nicht funktioniert
        //TODO: Tags zum testen heraussuchen
    }

    @Test
    public void testExcludeMethodsOnce(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-x", "setMethod1")));
        //TODO: zu testen: ist Anzahl der wsdl:message tags gleich?
        //TODO: sollte mit getTag(wsdl:definitions) machbar sein; danach die Anzahl zählen
    }

    @Test
    public void testExcludeMethodsTwice(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-x", "setMethod1 getMethod1")));
        //TODO: zu testen: ist Anzahl der wsdl:message tags gleich?
        //TODO: sollte mit getTag(wsdl:definitions) machbar sein; danach die Anzahl zählen
    }

    @Test
    public void testStopClasses(){
        String inClassName = "TestFile1";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-x", "WidgetPrice")));
        //TODO: zu testen: ist Anzahl der wsdl:message tags gleich?
        //TODO: zu testen: ist Anzahl der wsdl:operation tags gleich?
        //TODO: sollte mit getTag(wsdl:definitions) machbar sein; danach die Anzahl zählen
    }

    @Test
    public void testVersion1_1(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-T", "1.1")));
        //TODO: herausfinden, welche tags hier zu testen sind
    }

    @Test
    public void testVersion1_2(){
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-T", "1.2")));
        //TODO: herausfinden, welche tags hier zu testen sind
    }
}
