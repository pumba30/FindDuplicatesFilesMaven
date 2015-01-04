import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by pumba30 on 27.12.2014.
 */
public class SearchDuplicatesFiles implements Comparator<File> {

    //оригинальный файл как ключ, список  хранит список дубликатов файлов


    private ArrayList<File> foundFilesInDirectory; //список файлов заданной директории
    private ArrayList<File> possibleDuplicatesFiles; //возможные дубликаты файлов
    private HashMap<String, File> originFiles;


    private String path;


    public SearchDuplicatesFiles(String path) {
        this.path = path;
        foundFilesInDirectory = new ArrayList<File>();
        possibleDuplicatesFiles = new ArrayList<File>();
        originFiles = new HashMap<String, File>();
    }


    //добавим файлы в список, в котором будем искать дупликаты в заданной директории и
    //в поддиректориях
    public ArrayList getFilesInDirectory() throws IOException, NoSuchAlgorithmException {


        File file = new File(path);
        //создаем массив файлов из директории
        File[] files = file.listFiles();
        for (File item : files) {
            //если item файл, но не директория, добавляем в поисковый список
            if (item.isFile()) {
                foundFilesInDirectory.add(item);


            } else {
                path = item.getPath();
                getFilesInDirectory();
            }
        }
        return foundFilesInDirectory;
    }


    //метод подсчета хешкода для файла по алгоритму SHA1
    public String getSHA1CheckSum(File file) throws NoSuchAlgorithmException, IOException {
        //получим экземпляр для вычисления хешкода файла
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream inputStream = new FileInputStream(file);


        //хешкод будет вычислятся для первых 1024 байт (1 кБ) файла.
        byte[] dataBytes = new byte[1024];


        //read возвращает количество прочитанных байт. Если n = -1, конец файла
        int n = 0;
        try {
            while ((n = inputStream.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        //сохраним в массив вычисленный хешкод
        byte[] mdBytes = md.digest();


        //конвертируем из байтового в hex формат
        StringBuffer sb = new StringBuffer();
        String checkSumSHA1 = "";
        for (int i = 0; i < mdBytes.length; i++) {
            checkSumSHA1 = (sb.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16)
                    .substring(1))).toString();
        }
        return checkSumSHA1;
    }


    //метод отсортирует в список возможные дубликаты файлов по имени
    public ArrayList getPossibleDuplicatesFiles(ArrayList<File> fileList) {
        for (int i = 0; i < fileList.size(); i++) {
            String nameFileCheck = fileList.get(i).getName();
            for (int j = 0; j < fileList.size(); j++) {
                String nameFileToCompare = fileList.get(j).getName();
                if (i == j) {
                    continue;
                }  if (nameFileCheck.equals(nameFileToCompare)
                        || findByPattern(fileList.get(i)) == true) {
                    possibleDuplicatesFiles.add(fileList.get(i));
                    break;
                }
            }
        }
        return possibleDuplicatesFiles;
    }


    //метод отсортирует файл по паттерну
    //находим возможные дубликаты по шаблону pattern -
    //file.txt  - оригинал
    //file (1).txt -  дубликат, file - копия (1).txt - дубликат
    public boolean findByPattern(File file) {
        Pattern pattern = Pattern.compile("^(.+)((\\s-\\sкопия)|(\\s\\(\\d+\\)))(.*)$");
        Matcher matcher = pattern.matcher(file.getName());
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    //добавить в карту файли оригиналы по имени из списка дубликатов
    public HashMap getOriginFiles(ArrayList<File> files) {
        for (File file : files) {
            if (!originFiles.containsKey(file.getName())) {

                originFiles.put(file.getName(), file);
            }
        }
        return originFiles;
    }


    //метод для удаления файлов
    //@param fileList список файлов которые удаляем
    public void removeFiles(HashMap<String, File> fileList) {
        for (Map.Entry entry : fileList.entrySet()) {
            File file = (File) entry.getValue();
            file.delete();
            System.out.println(file + "  - deleted...");
        }
    }

    //метод для сравнения по размеру

    @Override
    public int compare(File o1, File o2) {
        return 0;
    }
}