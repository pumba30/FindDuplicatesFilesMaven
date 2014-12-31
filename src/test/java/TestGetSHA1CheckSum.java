import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pumba30 on 31.12.2014.
 */
public class TestGetSHA1CheckSum {
    @Test
    public void testingGetSHA1CheckSum() throws IOException, NoSuchAlgorithmException {
        //тестируемый класс SearchDuplicateFiles
        SearchDuplicatesFiles searchDuplicatesFiles = new SearchDuplicatesFiles("c:/testing");
        File file = new File("C:/testing/panda.jpg");

        //Проверяемый метод
         String s = searchDuplicatesFiles.getSHA1CheckSum(file);
        System.out.println(s);

    }



}
