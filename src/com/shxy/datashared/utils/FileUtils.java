package com.shxy.datashared.utils;

import org.nutz.mvc.upload.TempFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class FileUtils {
    private static final String ROOT_PATH = "E:\\software\\intellij\\intellij workspace\\DataSharedPlatform\\web\\img";
    private static final String URL_BASE = "http://192.168.0.103:8080/img/";
    private static final String IMAG_DIR = "img/";
    private static final Random r = new Random();

    public static File saveFile(TempFile tempFile) {
        //md5文件名
        String fileName = generateFileNameByMd5(tempFile.getFile().getAbsolutePath());
        if (fileName == null) {
            return null;
        }
        File file = new File(ROOT_PATH, fileName);
        try {
            tempFile.write(file.getPath());
//            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public static String getUrl(File file) {
        return IMAG_DIR + file.getName();
    }

    /**
     * 随机文件名
     *
     * @return
     */
    public static String generateFileName() {
        int length = 32;

        String numstr = "123456789";
        String chastr_b = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chastr_s = "abcdefghijklmnopqrstuvwxyz";
        String specil = "_";
        String base = numstr + chastr_b + chastr_s + specil;

        StringBuffer sb = new StringBuffer();

        sb.append(chastr_b.charAt(r.nextInt(chastr_b.length())));
        for (int i = 0; i < length; i++) {
            int num = r.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }

    /**
     * 通过文件生成md5文件名
     *
     * @param file 文件路径
     * @return 文件名称，有异常时，返回null
     */
    public static String generateFileNameByMd5(String file) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buff = new byte[8192];
            int length = 0;

            while ((length = inputStream.read(buff)) != -1) {
                messageDigest.update(buff, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return toHexString(messageDigest.digest()).append('.').append(file.substring(file.lastIndexOf(".") + 1)).toString();
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static StringBuilder toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb;
    }
}
