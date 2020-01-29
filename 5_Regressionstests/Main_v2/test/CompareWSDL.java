import java.util.List;
import com.predic8.wsdl.*;
import com.predic8.wsdl.diff.WsdlDiffGenerator;
import com.predic8.soamodel.Difference;

public class CompareWSDL {
    String cpOrig;
    String cpMod;
    String output;

    public CompareWSDL(String cpOrig, String cpMod, String output){
        this.cpMod=cpMod;
        this.cpOrig=cpOrig;
        this.output=output;


    }

    private static void compare(String cpOrig,String cpMod){
        WSDLParser parser = new WSDLParser();

        Definitions wsdl1 = parser.parse(cpOrig);

        Definitions wsdl2 = parser.parse(cpMod);

        WsdlDiffGenerator diffGen = new WsdlDiffGenerator(wsdl1, wsdl2);
        List<Difference> lst = diffGen.compare();
        for (Difference diff : lst) {
            dumpDiff(diff, "");
        }
    }

    private static void dumpDiff(Difference diff, String level) {
        System.out.println(level + diff.getDescription());
        for (Difference localDiff : diff.getDiffs()){
            dumpDiff(localDiff, level + "  ");
        }
    }
}