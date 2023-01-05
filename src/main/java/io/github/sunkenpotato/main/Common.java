package io.github.sunkenpotato.main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static io.github.sunkenpotato.main.Main.LOGGER;

public class Common {
    /**
     * Commonly used method for reading and returning image files
     * @param path Path to image
     * @exception IOException thrown most commonly if the file is not found
     * @return {@code BufferedImage}
     */
    public static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(Common.class.getResourceAsStream(path)));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return image;
    }

    /**
     * Method for reading files.
     * @param path File path
     * @return {@code String}
     */
    public static String readFile(String path) {
        StringBuilder ct = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(new File(path))) {

            int ch;
            ct = new StringBuilder();
            while ((ch = fis.read()) != -1) {
                ct.append((char) ch);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return String.valueOf(ct);
    }

    /**
     * Method called on exit
     */
    public static void exit() {
        Path src = Paths.get("logs/latest.log");
        String out = "logs/" + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm")) + "m.zip";
        zipFile(src, out);

    }

    /**
     * Zip a file.
     * @param source input file
     * @param out output file
     */
    static void zipFile(Path source, String out){
        if (!Files.isRegularFile(source)) {
            LOGGER.error("File without opaque content provided.");
            return;
        }


        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(out));
            FileInputStream fis = new FileInputStream(source.toFile())
        ) {
            ZipEntry zipEntry = new ZipEntry(source.getFileName().toString());
            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, len);
            }
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

}