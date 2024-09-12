package com.gaotianchi.resource;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {
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

    public static String getImageUrl(HttpServletRequest req, String filename) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/images/" + filename.replace("\\", "/");
    }
}
