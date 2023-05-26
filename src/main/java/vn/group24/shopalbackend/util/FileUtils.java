package vn.group24.shopalbackend.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ttg
 */
@Slf4j
public class FileUtils {

    public static String saveFileWithRandomName(MultipartFile file, String direction) throws IOException {
        makeDirectoryIfNotExist(direction);
        String fileName = UUID.randomUUID().toString().concat(".").concat(Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())));
        Path fileNamePath = Paths.get(direction, fileName);
        Files.write(fileNamePath, file.getBytes());
        return fileName;
    }

    public static void deleteFile(MultipartFile file, String direction) {
        Path fileNamePath = Paths.get(direction, getFileName(file));
        try {
            Files.deleteIfExists(fileNamePath);
        } catch (NoSuchFileException e) {
            log.error("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            log.error("Directory is not empty.");
        } catch (IOException e) {
            log.error("Error when delete file: " + e.getMessage());
        }
    }

    public static void writeFile(String str, String fileName, String direction) throws IOException {

        Path path = Paths.get(direction, fileName);
        byte[] strToBytes = str.getBytes();

        Files.write(path, strToBytes);

        Files.readAllLines(path);
    }

    public static void deleteFile(String fileName, String direction) {
        Path fileNamePath = Paths.get(direction, fileName);
        try {
            Files.deleteIfExists(fileNamePath);
        } catch (NoSuchFileException e) {
            log.error("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            log.error("Directory is not empty.");
        } catch (IOException e) {
            log.error("Error when delete file: " + e.getMessage());
        }
    }

    private static void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private static String getFileName(MultipartFile file) {
        return file.getName().concat(".").concat(Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())));
    }
}
