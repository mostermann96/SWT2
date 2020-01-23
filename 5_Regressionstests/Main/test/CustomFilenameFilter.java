import java.io.File;
import java.io.FilenameFilter;

public class CustomFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File file, String s) {
        return !s.startsWith(".");
    }
}
