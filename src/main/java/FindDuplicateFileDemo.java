import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pumba30 on 30.12.2014.
 */
public class FindDuplicateFileDemo {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {


        SearchDuplicatesFiles searchDuplicatesFiles = new SearchDuplicatesFiles("c:/testing");
        ArrayList<File> listFoundFiles = searchDuplicatesFiles.getFilesInDirectory();


        System.out.println("Файлы найденные в директории:\n");
        listToScreen(listFoundFiles);


        ArrayList<File> possibleDuplFiles = searchDuplicatesFiles.getPossibleDuplicatesFiles(listFoundFiles);
        System.out.println("Возможные дубликаты:\n");
        listToScreen(possibleDuplFiles);


        System.out.println("\nВыделенные из списка дубликатов файлы дубликатов без повторения:");
        HashMap<String, File> fileHashMap = searchDuplicatesFiles.getOriginFiles(possibleDuplFiles);
        for (Map.Entry entry : fileHashMap.entrySet()) {
            File file = (File) entry.getValue();


            System.out.println(file.getName());
        }


        // searchDuplicatesFiles.removeFiles(fileHashMap);


    }


    public static void listToScreen(List<File> list) {
        for (File item : list) {
            System.out.println(item);
        }
    }
}
