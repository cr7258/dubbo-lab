package com.chengzw.framework.register;

import com.chengzw.framework.URL;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地文件写入读取服务端信息
 * @author 程治玮
 * @since 2021/3/31 12:02 上午
 */
public class FileMapRegister {

    private static Map<String, String> REGISTER = new HashMap<>();


    public static void regist(String interfaceName, String implClass, String url) {
        REGISTER.put(interfaceName, implClass + "::" + url);
        saveFile();
    }

    public static URL getURL(String interfaceName) {
        REGISTER = getFile();
        String[] s = REGISTER.get(interfaceName).split("::")[1].split(":");
        URL url = new URL(s[0],Integer.parseInt(s[1]));
        return url;
    }

    public static Class getImplClass(String interfaceName) throws ClassNotFoundException {
        REGISTER = getFile();
        return Class.forName(REGISTER.get(interfaceName).split("::")[0]);
    }

    public static void saveFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/chengzhiwei/Desktop/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/chengzhiwei/Desktop/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, String>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
