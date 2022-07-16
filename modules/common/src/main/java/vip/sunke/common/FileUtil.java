package vip.sunke.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunke
 * @Date 2017/6/9 14:49
 * @description
 */

public class FileUtil {


    public static void writeInputStreamToOutputStream(InputStream stream, OutputStream outputStream) throws Exception {





        byte buff[] = new byte[1024];
        int length = 0;
        try {
            while ((length = stream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }


        } catch (IOException e) {

        } finally {
            try {
                stream.close();

                outputStream.close();

                outputStream.flush();
            } catch (Exception e) {

            }
        }


    }


    public static void getFolderFiles(File f, String suffix, List<String> fileNameList, String fontFilePath) {


        if (f != null) {


            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {

                        if (fileArray[i].isFile() && !fileArray[i].isHidden()) {
                            if (suffix != null && !"".equals(suffix) && fileArray[i].getName().endsWith("." + suffix)) {


                                fileNameList.add((fileArray[i].getAbsolutePath().replace(fontFilePath, "")).replace("\\", "\\/"));

                            }


                        } else { //递归调用
                            getFolderFiles(fileArray[i], suffix, fileNameList, fontFilePath);
                        }


                    }
                }
            } else {
                //System.out.println(f);
            }
        }


    }


    /**
     * 得到目录下的所有文件名,
     *
     * @param path
     * @return
     */
    public static List<String> getFolderFileNames(String path) {
        File dirFile = new File(path);
        if (!dirFile.isDirectory())
            return null;
        File fa[] = dirFile.listFiles();
        if (fa == null || fa.length == 0)
            return null;
        int len = fa.length;
        List<String> fileNameList = new ArrayList<String>();
        File fs = null;
        for (int i = 0; i < len; i++) {
            ;
            fs = fa[i];
            if (fs.isFile() && !fs.isHidden()) {

                fileNameList.add(fs.getName());


            }

        }


        return fileNameList;

    }


    // 保存文件
    public static void saveFile(String newsRootPath, String filename,
                                File picFile) {
        try {
            File newsFileRoot = new File(newsRootPath);
            if (!newsFileRoot.exists()) {
                newsFileRoot.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(newsRootPath + filename);
            FileInputStream fis = new FileInputStream(picFile);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static boolean exists(String filePath) {

        File file = new File(filePath);

        return file.exists();

    }

    // 删除文件
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    // 删除文件与目录
    public static boolean deleteFolder(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }

    // 删除目录
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


    public static String getClassPath() {

        String path = Thread.currentThread().getContextClassLoader()
                .getResource("").toString();
        path = path.replace('/', '\\'); // 将/换成\
        path = path.replace("file:", ""); // 去掉file:
        path = path.replace("classes\\", ""); // 去掉class\

        if (path.contains(":"))
            path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
        path = path.replace("\\", File.separator);

        return path;


    }

    public static boolean mkdirs(String path) {
        File file = new File(path);

        if (!file.exists())

            return file.mkdirs();
        return true;

    }


    public static boolean renameTo(File sourceFile, String filePath) {


        File targetFile = new File(filePath);

        return renameTo(sourceFile, targetFile);
    }


    public static boolean renameTo(File sourceFile, File targetFile) {

        if (sourceFile == null || targetFile == null)
            return false;

        return sourceFile.renameTo(targetFile);


    }


    public static String readInInputStream(InputStream inputStream) {


        InputStreamReader fr = null;


        try {

            fr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(fr);

            StringBuffer sf = new StringBuffer("");
            String s;
            while ((s = br.readLine()) != null) {
                sf.append(s);


            }

            return sf.toString();


        } catch (Exception e) {
            return "";
        } finally {
            try {
                if (fr != null)
                    fr.close();

            } catch (Exception e) {

            }


        }


    }


    /**
     * 读取文本文件
     *
     * @param fileName
     * @return
     */
    public static String readInFile(String fileName) {
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);

            BufferedReader br = new BufferedReader(fr);
            StringBuffer sf = new StringBuffer("");
            String s;
            while ((s = br.readLine()) != null) {
                sf.append(s);
            }

            return sf.toString();


        } catch (Exception e) {
            return "";
        } finally {
            try {
                if (fr != null)
                    fr.close();

            } catch (Exception e) {

            }


        }


    }


    public static void writeInFile(String fileDir, String fileName, String writeContent) {

        FileUtil.mkdirs(fileDir);


        writeInFile(fileDir + File.separator + fileName, writeContent);

    }


    public static void writeInFile(String fileName, String writeContent, boolean append) {




        File f = new File(fileName);

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (f.exists() && !append) {
                f.delete();
            }

            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), append);  //true表示可以追加新内容
            //fw = new FileWriter(f.getAbsoluteFile()); //表示不追加
            bw = new BufferedWriter(fw);
            bw.write(writeContent);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void writeInFile(String fileName, String writeContent) {
        writeInFile(fileName, writeContent, false);

    }


}
