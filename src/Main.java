import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        GameProgress player1 = new GameProgress(90, 2, 1, 54.67);
        GameProgress player2 = new GameProgress(54, 4, 3, 154.99);
        GameProgress player3 = new GameProgress(82, 7, 4, 445.08);
        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);

        saveGame("E:\\\\Java\\\\Netology\\\\Projects\\\\JavaCore\\\\1.3_Input-Output\\\\Games\\\\savegames\\\\player1Saving.dat", player1);
        saveGame("E:\\Java\\Netology\\Projects\\JavaCore\\1.3_Input-Output\\Games\\savegames\\player2Saving.dat", player2);
        saveGame("E:\\Java\\Netology\\Projects\\JavaCore\\1.3_Input-Output\\Games\\savegames\\player3Saving.dat", player3);


        String archivePath = "E:\\Java\\Netology\\Projects\\JavaCore\\1.3_Input-Output\\Games\\savegames\\zipSavings.zip";
        String folderPath = "E:\\Java\\Netology\\Projects\\JavaCore\\1.3_Input-Output\\Games\\savegames";
        zipFiles(archivePath, folderPath);


        File filesSource = new File(folderPath);
        for (File item : filesSource.listFiles()) {
            if (item.getName().contains(".dat")) {
                if (item.delete()) {
                    System.out.println("Файл удален");
                } else {
                    System.out.println(item.getName() + " не удален");
                }
            }
        }
    }

    public static void saveGame(String path, GameProgress player) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(player);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void zipFiles(String archivePath, String folder) throws Exception {
        File fileSource = new File(folder);
        File[] files = fileSource.listFiles();
        FileOutputStream fout = new FileOutputStream(archivePath);
        ZipOutputStream zout = new ZipOutputStream(fout);
        for (int i = 0; i < files.length; i++) {
            try {
                FileInputStream fis = new FileInputStream(files[i]);
                ZipEntry entry = new ZipEntry("Player" + (i + 1) + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        zout.close();
        fout.close();
    }

}