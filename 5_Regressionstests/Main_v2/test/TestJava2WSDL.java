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
            fail();
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
        runner.runJava2WSDL(false,"javax.xml.messaging.URLEndpoint", Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice"), true, true);
        // schreibt gleiche wsdl wie Zeile bevor
        runner.runJava2WSDL(false,"javax.xml.messaging.URLEndpoint", null, true, true);
        runner.runJava2WSDL(false,null, Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice"), true, true);
        runner.runJava2WSDL(false,null, null, true, true);
        // schreibt gleiche txt wie Zeile bevor
        runner.runJava2WSDL(null, null);
        runner.runJava2WSDL(false,null, Arrays.asList("--help"), true, true);
        // schreibt gleiche txt wie Zeile bevor
        runner.runJava2WSDL(null, Arrays.asList("--help"));
    }

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     * java org.apache.axis.wsdl.Java2WSDL -o test_output/WidgetPrice.orig.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
     * java org.apache.axis.wsdl.Java2WSDL -o test_output/WidgetPrice.mod.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
     */
    @Test
    public void testCorrectOutputFilesURLEndpointWidget_l_o() {
        String inClassName = "javax.xml.messaging.URLEndpoint";
        List<String> options = Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice");
        requireSuccess(runner.runJava2WSDL(inClassName, options));
        //assertTrue(new File(runner.getOutputFilenames(inClassName)[0]).exists());
        //assertTrue(new File(runner.getOutputFilenames(inClassName)[1]).exists());
        assertEquals(readFile(runner.getOutputFilenames("URLEndpoint"+runner.getEndOfFileName(options,"-o"))[0]), readFile(runner.findOutputFile("URLEndpoint"+runner.getEndOfFileName(options,"-o"))));
    }

    /**
     * Übereinstimmung von Konsolen-Ausgabe bei Angabe von Option --help
     */
    @Test
    public void testOptionHelp(){
        List<String> options = Arrays.asList("--help");
        requireEqualExitCodes(runner.runJava2WSDL(false,null, options, true,true));
        String[] outFilenames = runner.getConsoleOutputFilenames(null, options);
        assertEquals(readFile(outFilenames[0]), readFile(outFilenames[1]));
    }

    /**
     * Übereinstimmung der WSDL-Ausgabe bei Eingabe mit Debug-Infos
     * Die Referenz gibt an, dass bei Eingabe einer Klasse, die mit allen Debug-Informationen kompiliert wurde,
     * die Methoden-Parameternamen ausgelesen und benutzt werden.
     * TODO robuster WSDL Vergleich (Vergleich schlägt schon wegen verschiedenen Erstellungs-Zeiten der Dateien im Header fehl)
     */
    @Test
    public void testRunWithDebugInfo_l_o(){
        String inClassName = "WidgetPriceDebug";
        List<String> options=Arrays.asList("-l", "someLocation");
        requireSuccess(runner.runJava2WSDL(inClassName,options));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-o"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-o"))));
    }

    @Test
    public void testRunWithDebugInfo_l_O(){
        String inClassName = "WidgetPriceDebug";
        List<String> options=Arrays.asList("-l", "someLocation");
        requireSuccess(runner.runJava2WSDL(true,inClassName,options,false,false));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-O"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-O"))));
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

    /**
     * Übereinstimmung der WSDL-Ausgabe bei Eingabe mit Debug-Infos
     * Die Referenz gibt an, dass bei Eingabe einer Klasse, die mit allen Debug-Informationen kompiliert wurde,
     * die Methoden-Parameternamen ausgelesen und benutzt werden.
     * TODO robuster WSDL Vergleich (Vergleich schlägt schon wegen verschiedenen Erstellungs-Zeiten der Dateien im Header fehl)
     */
    @Test
    public void testRunWithCalculatorClass_l_o(){
        String inClassName = "Calculator";
        List<String> options=Arrays.asList("-l", "someLocation");
        requireSuccess(runner.runJava2WSDL(inClassName,options));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-o"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-o"))));
    }
    @Test
    public void testRunWithCalculatorClass_l_O(){
        String inClassName = "Calculator";
        List<String> options=Arrays.asList("-l", "someLocation");
        requireSuccess(runner.runJava2WSDL(true,inClassName,options,false,false));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-O"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-O"))));
    }

    @Test
    public void testRunWithCalculatorClass_l_n_o(){
        String inClassName = "Calculator";
        List<String> options=Arrays.asList("-l", "someLocation", "-n", "calc");
        requireSuccess(runner.runJava2WSDL(inClassName,options));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-o"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-o"))));
    }

    /**
     * java org.apache.axis.wsdl.Java2WSDL -a Calculator -l "someLocation" -o Calculator-a.orig.wsdl oder Calculator-a.mod.wsdl
     * --all or -a look for allowed methods in inherited class
     */
    @Test
    public void testRunWithCalculatorClass_a_l_o(){
        String inClassName = "Calculator";
        List<String> options= Arrays.asList("-a", inClassName,"-l", "someLocation");
        requireSuccess(runner.runJava2WSDL(false,inClassName, options
                ,false, true));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-o"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-o"))));
    }


    /**
     * java org.apache.axis.wsdl.Java2WSDL -a Calculator -l "someLocation" -o Calculator-a.orig.wsdl oder Calculator-a.mod.wsdl
     * --all or -a look for allowed methods in inherited class
     */
    @Test
    public void testRunWithCalculatorImpl_i_l_o(){
        String inClassName = "Calculator";
        List<String> options=Arrays.asList("-i","CalculatorImpl","-l", "someLocation");
        requireSuccess(runner.runJava2WSDL(true,inClassName, options
                ,false,true));

        // Test, ob sich normale wsdl von wsdl mit Debug-Infos unterscheidet (sie tun es nicht)
        //requireSuccess(runner.runJava2WSDL("WidgetPrice", Arrays.asList("-l", "someLocation")));
        //assertEquals(readFile(runner.getOutputFilenames(inClassName)[0]), readFile(runner.getOutputFilenames("WidgetPrice")[0]));

        assertEquals(readFile(runner.getOutputFilenames(inClassName+runner.getEndOfFileName(options,"-o"))[0]), readFile(runner.findOutputFile(inClassName+runner.getEndOfFileName(options,"-o"))));
    }

}
