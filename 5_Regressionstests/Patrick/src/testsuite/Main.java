package testsuite;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Main {
    
    static String inputDir = "in";
    static String outputDir = "out";
    
    static String[] cmd = {
        "java", "org.apache.axis.wsdl.Java2WSDL"/*,
        "-o", "test.wsdl",
        "-n", "\"urn:Test\"", 
        "-p", "\"example\"", "\"urn:Test\"", 
        "example.WidgetPrice"*/
    };
    static String origCP = "axis-1_4/lib/*";
    static String modCP = "axis-modified.jar:axis-1_4/lib/*";
    
    public static void main(String[] args)  {
        new Main().run();
    }

    public boolean runJava2WSDL(String classpath, String inPath, String outPath){ // TODO: paths
        ProcessBuilder builder = new ProcessBuilder(Arrays.asList(cmd))
                .inheritIO()
                .directory(null);
        
        Map<String, String> env = builder.environment();
        env.put("CLASSPATH", classpath);
        System.out.println(outPath);
        //System.out.println(builder.command());
        //System.out.println(env);
        
        Process process;
        try{
            process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e){
            System.out.println("Exception caught: " + e.toString());
            return false;
        }
        
        return process.exitValue() == 0;
    }
    
    public void run() {
        File in = new File(inputDir);
        File out = new File(outputDir);
        
        if (!in.exists())
            in.mkdir();
        if (!out.exists())
            out.mkdir();
        
        // Ausgabeverz. leeren
        File[] outFiles = out.listFiles();
        for (File f : outFiles){
            f.delete();
        }
        
        // Hauptschleife
        File[] inFiles = in.listFiles();
        for (File f : inFiles){
            String filename = f.getName().substring(0, f.getName().lastIndexOf("."));
            if (runJava2WSDL(origCP, f.getPath(), 
                    new File(outputDir, filename + ".orig.wsdl").getPath())){
                System.err.println("Fehler 1");
                break;
            }
            
            if (runJava2WSDL(modCP, f.getPath(), 
                    new File(outputDir, filename + ".mod.wsdl").getPath())){
                System.err.println("Fehler 2");
                break;
            }
            
            // TODO Vergleich
        }
    }
    
}
 