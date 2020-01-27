import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestJava2WSDL {


    /**
     * Stellt sicher, dass die Tool-Aufrufe erfolgreich sind
     *
     * @param succeeded Rückgabewert von runJava2WSDL
     */
    private void requireSuccess(boolean[] succeeded) {
        assertTrue(succeeded[0]);
        assertTrue(succeeded[1]);
    }


/*
    @BeforeClass
    public static void cleanOutDir(){
        File[] files = new File(java2WSDLRunner.getOutputDir()).listFiles(new CustomFilenameFilter());
        if (files != null) {
            for (File f : files)
                f.delete();
        }
    }
*/

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     * java org.apache.axis.wsdl.Java2WSDL -o test_output/WidgetPrice.orig.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
     * java org.apache.axis.wsdl.Java2WSDL -o test_output/WidgetPrice.mod.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
     */

    @Test
    public void testCorrectOutputFilesURLEndpointWidget() {
        ConsoleRunner consoleRunner = new ConsoleRunner(Arrays.asList("java","org.apache.axis.wsdl.WSDL2Java"));
        String inPath = consoleRunner.getInputDir() + File.separator + "WidgetPrice.java";
        String fileExtention = "wsdl";
        String outPutCommand = "-o";
        List<String> forOutput = Arrays.asList("WidgetPrice", fileExtention, outPutCommand);
        requireSuccess(consoleRunner.runJava2WSDL(forOutput, "javax.xml.messaging.URLEndpoint", Arrays.asList("-l", "http://localhost:8080/axis/services/WidgetPrice")));
        String outPath0 = consoleRunner.getOutputDir() + File.separator + consoleRunner.getOutputFilenames(forOutput)[0];
        String outPath1 = consoleRunner.getOutputDir() + File.separator + consoleRunner.getOutputFilenames(forOutput)[1];
        assertTrue(new File(outPath0).exists());
        assertFalse(new File(outPath1).exists());
        assertTrue(new File("test_output/WidgetPrice_mod_impl.wsdl").exists());

    }

    /**
     * Teste, ob Ausgabedateien unter korrektem Namen angelegt werden
     * java org.apache.axis.wsdl.Java2WSDL > test_output/help_orig.txt"
     * java org.apache.axis.wsdl.Java2WSDL > test_output/help_mod.txt"
     */
    @Test
    public void testCorrectOutputFilesHelp() {
        ConsoleRunner consoleRunner = new ConsoleRunner(Arrays.asList("java","org.apache.axis.wsdl.WSDL2Java"));
        //String inPath = java2WSDLRunner.getInputDir() + File.separator + "Help.java";
        String fileExtension = "txt";
        String outPutCommand = ">";

        List<String> forOutput = Arrays.asList("help", fileExtension, outPutCommand);
        requireSuccess(consoleRunner.runJava2WSDL(forOutput,"", Collections.emptyList()));
        String outPath0 = consoleRunner.getOutputDir() + File.separator + consoleRunner.getOutputFilenames(forOutput)[0];
        String outPath1 = consoleRunner.getOutputDir() + File.separator + consoleRunner.getOutputFilenames(forOutput)[1];

        assertTrue(new File(outPath0).exists());
        assertTrue(new File(outPath1).exists());
    }

}
