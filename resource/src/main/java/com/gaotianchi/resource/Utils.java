package com.gaotianchi.resource;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            int lastDotIndex = fileName.lastIndexOf('.');
            return fileName.substring(lastDotIndex);
        }
        throw new InvalidFileNameException(fileName, "检索不到文件格式！");
    }

    public static String generateUniqueFileName() {
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = sdf.format(new Date(timestamp));
        int randomNum = new Random().nextInt(10000);
        return formattedDate + randomNum;
    }

    public static Path getImageFullPath(String root, String filename, CompressionLevel compressionLevel) {
        Path rootPath = Paths.get(root);
        String fullFileName = filename + File.separator + compressionLevel.name() + "." + filename;
        return rootPath.resolve(fullFileName);
    }

    public static String getImageUrl(HttpServletRequest req, String filename) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/images" + filename.replace("\\", "/");
    }
}
