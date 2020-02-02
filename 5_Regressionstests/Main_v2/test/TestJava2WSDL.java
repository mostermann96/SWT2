import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;



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

    /**
     *
     * @param inputFile Dateipfad der zu untersuchenden WSDL Datei
     * @param tag der zu findende Tag
     * @return ein Set aller relevanten Tags
     */
    private Set<String> getTag(String inputFile, String tag) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        if (tag == "" || tag == null) {
            tag = "wsdl:definitions";
        }
        Set<String> tags = new HashSet<>();
        NodeList list = doc.getElementsByTagName(tag);
        for (int j = 0; j < list.getLength(); j++) {
            tags.add(printNode(list.item(j), "", 0));
        }
        return tags;
    }

    /**
     *
     * @param file Dateipfad der zu untersuchenden WSDL Datei
     * @param tag der zu findende Tag
     * @param attr name des Attributes
     * @return ein Set aller relevanten Tags
     */
    private static Set<String> getTag(String file, String tag, String attr) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        if(tag == "" || tag == null){
            tag = "wsdl:definitions";
        }
        Set<String> tags = new HashSet<>();
        NodeList list =doc.getElementsByTagName(tag);
        for(int j=0; j<list.getLength();j++) {
            tags.add(printNode(list.item(j), attr, 0));
        }
        return tags;
    }

    private static NodeList getNodeList(String file, String tag) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        if(tag == "" || tag == null){
            tag = "wsdl:definitions";
        }
        return doc.getElementsByTagName(tag);

    }

    private static boolean nodesAreEqual(Node node1, Node node2){
        if(!Objects.equals(node1.getNodeName(), node2.getNodeName())) {
            return false;
        }
        if(!Objects.equals(node1.getNodeValue(), node2.getNodeValue())){
            return false;
        }
        if(node1.hasAttributes() || node2.hasAttributes()){
            if(!node1.hasAttributes()){
                return false;
            }
            if(!node2.hasAttributes()){
                return false;
            }
            if(node1.getAttributes().getLength()!=node2.getAttributes().getLength()){
                return false;
            }
            for(int i=0;i<node1.getAttributes().getLength();i++){
                if(!nodesAreEqual(node1.getAttributes().item(i),node2.getAttributes().item(i))){
                    return false;
                }
            }
        }
        if(!nodeListsAreEqual(node1.getChildNodes(), node2.getChildNodes())){
            return false;
        }
        return true;
    }

    /**
     *
     * @param list1
     * @param list2
     * @return true iff die Nodes der Liste gleich sind, oder nur vertauscht
     */
    private static boolean nodeListsAreEqual(NodeList list1, NodeList list2){
        if(list1.getLength()!=list2.getLength()) {
            System.out.println("different length of lists");
            return false;
        }
        //checks if there is every node in list1 is also contained in list2
        for(int i=0; i<list1.getLength();i++){
            Node node1 = list1.item(i);

            for(int j=0;j<list2.getLength();j++){
                Node node2 = list2.item(j);
                if(nodesAreEqual(node1,node2)){
                    break;
                }
                //if this is true, then there is no element node1 in list2
                if(j==list2.getLength()-1){
                    System.out.println("The node "+node1.getNodeName()+" isn't either not contained in the modified version, or has some changed attributes there");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param node der zu untersuchende DOM-Node
     * @param filter Attributname
     * @param indention zur übersichtlicheren Darstellung
     * @return eine Stringrepräsentation des tags
     */
    private static String printNode(Node node, String filter, int indention) {
        String temp = "";
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            if (filter == "" || filter == null) {
                temp += String.join("", Collections.nCopies(indention, "\t"));
                temp += ("Node Name: " + node.getNodeName() + " Value: " + node.getNodeValue() + "\n");
            }
            if (node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    Node tempNode = nodeMap.item(i);
                    if (filter == "" | filter == null || tempNode.getNodeName() == filter || tempNode.getNodeName() == filter) {
                        temp += String.join("", Collections.nCopies(indention, "\t"));
                        temp += ("\tNode Attribute:" + tempNode.getNodeName() + " Value:" + tempNode.getNodeValue() + "\n");
                    }
                }
            }
        }
        if (node.hasChildNodes()) {
            for(int i=0; i<node.getChildNodes().getLength();i++)
            temp += printNode(node.getChildNodes().item(i), filter, indention+1);
        }
        return temp;
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
    public void testLocationOption() throws ParserConfigurationException, SAXException, IOException {
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
    public void testOptionInput() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation",
                "-I", "test_input"+File.separator+"dummy_input.wsdl")));
        /*assertEquals("-I hat nicht richtig importiert",
                getTag(runner.getWsdlOut()[0], "wsdl:message", "dummy"),
                getTag(runner.findOutputFile(), "wsdl:message", "dummy"));
        assertEquals("-I hat nicht richtig importiert",
                getTag(runner.getWsdlOut()[0], "wsdl:service"),
                getTag(runner.findOutputFile(), "wsdl:service"));*/
        assertTrue(nodeListsAreEqual(getNodeList(runner.getWsdlOut()[0],"wsdl:service"),
                getNodeList(runner.findOutputFile(), "wsdl:service")));
    }

    @Test
    public void testPortNameOverwrite() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-s", "alteredPortName")));
        assertEquals("-s hat portname nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:port"), getTag(runner.findOutputFile(), "wsdl:port"));
    }

    @Test
    public void testPortTypeNameOverwrite() throws IOException, SAXException, ParserConfigurationException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation",
                "-P", "alteredTypeName")));
        assertTrue(nodeListsAreEqual(getNodeList(runner.getWsdlOut()[0],"wsdl:portType"),
                getNodeList(runner.findOutputFile(), "wsdl:portType")));
        //assertEquals("-P hat portTypeName nicht richtig überschrieben",
        //        getTag(runner.getWsdlOut()[0], "wsdl:portType"),
        //       getTag(runner.findOutputFile(), "wsdl:portType"));
    }

    @Test
    public void testBindingNameOverwrite() throws IOException, SAXException, ParserConfigurationException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-s", "alteredPortName",
                "-b", "someBindingName")));
        assertEquals("-b hat Name des Bindings nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:binding", "someBindingName"),
                getTag(runner.findOutputFile(), "wsdl:binding", "someBindingName"));
    }

    @Test
    public void testServiceElementNameOverwrite() throws IOException, SAXException, ParserConfigurationException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-S", "alteredServiceElementName")));
        assertEquals("-S hat Name des Services nicht richtig überschrieben",
                getTag(runner.getWsdlOut()[0], "wsdl:service", "alteredServiceElementName"),
                getTag(runner.findOutputFile(), "wsdl:service", "alteredServiceElementName"));
    }

    @Test
    public void testTargetNameSpaceOverwrite() throws ParserConfigurationException, SAXException, IOException {
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
    public void testMethodsOnce() throws IOException, SAXException, ParserConfigurationException {
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
    public void testMethodsTwice() throws IOException, SAXException, ParserConfigurationException {
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
    public void testOutputModeAll() throws IOException, SAXException, ParserConfigurationException {
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
    public void testOutputModeInterface() throws IOException, SAXException, ParserConfigurationException {
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
    public void testOutputModeImplementation() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "Implementation",
                "-L", "test_input/dummy_input.wsdl")));
        assertEquals("-W \"All\" hat portType nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:import"),
                getTag(runner.findOutputFile(), "wsdl:import"));
    }

    @Test
    public void testImplementationNameSpace() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-w", "All",
                "-N", "http://example.org")));
        assertEquals("-N hat implementation Namespace nicht richtig generiert",
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

    @Test
    public void testSoapActionDefault() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-A", "DEFAULT")));
        assertEquals("-A \"DEFAULT\" hat soapAction nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:binding"),
                getTag(runner.findOutputFile(), "wsdl:binding"));
        //TODO: hier muss wahrscheinlich noch mehr Logik angewandt werden.
        //TODO: Ziel: nur wsdlsoap:operation soapAction testen, aber alle Vorkommen davon
    }

    @Test
    public void testSoapActionOperation() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-A", "OPERATION")));
        assertEquals("-A \"OPERATION\" hat soapAction nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:binding"),
                getTag(runner.findOutputFile(), "wsdl:binding"));
        //TODO: hier muss wahrscheinlich noch mehr Logik angewandt werden.
        //TODO: Ziel: nur wsdlsoap:operation soapAction testen, aber alle Vorkommen davon
    }

    @Test
    public void testSoapActionNone() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-A", "NONE")));
        assertEquals("-A \"NONE\" hat soapAction nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:binding"),
                getTag(runner.findOutputFile(), "wsdl:binding"));
        //TODO: hier muss wahrscheinlich noch mehr Logik angewandt werden.
        //TODO: Ziel: nur wsdlsoap:operation soapAction testen, aber alle Vorkommen davon
    }

    @Test
    public void testRPCStyle() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-y", "RPC")));
        assertEquals("-y \"RPC\" wurde nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //testet gesamtes Dokument, also falls irgendwo ein Fehler, dann schlägt dieser Test fehl
    }

    @Test
    public void testDOCUMENTStyle() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-y", "DOCUMENT")));
        assertEquals("-y \"DOCUMENT\" wurde nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //testet gesamtes Dokument, also falls irgendwo ein Fehler, dann schlägt dieser Test fehl
    }

    @Test
    public void testWRAPPEDStyle() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-y", "WRAPPED")));
        assertEquals("-y \"WRAPPED\" wurde nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //testet gesamtes Dokument, also falls irgendwo ein Fehler, dann schlägt dieser Test fehl
    }

    @Test
    public void testLITERALUse() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-u", "LITERAL")));
        assertEquals("-u \"LITERAL\" wurde nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //testet gesamtes Dokument, also falls irgendwo ein Fehler, dann schlägt dieser Test fehl
    }

    @Test
    public void testENCODEDUse() throws ParserConfigurationException, SAXException, IOException {
        String inClassName = "WidgetPrice";
        requireSuccess(runner.runJava2WSDL(inClassName, Arrays.asList("-l", "someLocation/portName",
                "-u", "ENCODED")));
        assertEquals("-u \"ENCODED\" wurde nicht richtig generiert",
                getTag(runner.getWsdlOut()[0], "wsdl:definitions"),
                getTag(runner.findOutputFile(), "wsdl:definitions"));
        //testet gesamtes Dokument, also falls irgendwo ein Fehler, dann schlägt dieser Test fehl
    }

}
