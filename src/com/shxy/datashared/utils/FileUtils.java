package com.shxy.datashared.utils;

import org.nutz.mvc.upload.TempFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    private static final String ROOT_PATH = "E:\\software\\intellij\\intellij workspace\\DataSharedPlatform\\web\\img";
    private static final String URL_BASE = "http://192.168.0.103/img/";

    public static File saveFile(TempFile tempFile) {
        File file = new File(ROOT_PATH, tempFile.getName());
        try {
            tempFile.write(file.getPath());
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public static String getUrl(File file){
        return URL_BASE + file.getName();
    }

}
