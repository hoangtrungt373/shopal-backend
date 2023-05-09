package vn.group24.shopalbackend.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ttg
 */
public class FileUtils {

    public static String saveFileWithRandomName(MultipartFile file, String direction) throws IOException {
        makeDirectoryIfNotExist(direction);
        String fileName = UUID.randomUUID().toString().concat(".").concat(Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())));
        Path fileNamePath = Paths.get(direction, fileName);
        Files.write(fileNamePath, file.getBytes());
        return fileName;
    }

    private static void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
