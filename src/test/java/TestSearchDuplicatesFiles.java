import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pumba30 on 31.12.2014.
 */
public class TestSearchDuplicatesFiles {
    public SearchDuplicatesFiles searchDuplicatesFiles = null;

    @Test
    public void testingGetSHA1CheckSum() throws IOException, NoSuchAlgorithmException {
        File file = new File("C:/testing/panda.txt");
        //Проверяемый метод
        String s = searchDuplicatesFiles.getSHA1CheckSum(file);
        System.out.println(s);
    }

    @Before
    public void setup() {
        searchDuplicatesFiles = new SearchDuplicatesFiles("c:/testing");
    }


}
