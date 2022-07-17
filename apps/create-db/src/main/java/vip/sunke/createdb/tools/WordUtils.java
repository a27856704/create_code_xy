package vip.sunke.createdb.tools;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sunke
 * @Date 2020/7/29 14:51
 * @description
 */

public class WordUtils {


    /**
     * 生成word
     * @param templatePath
     * @param outputStream
     * @param config
     * @param dataMap
     * @param wordListen
     * @return
     */
    public static boolean createWord(String templatePath, OutputStream outputStream, Configure config, Map<String, Object> dataMap, WordListen wordListen) {
        try {
            XWPFTemplate template = XWPFTemplate.compile(templatePath, config).render(dataMap);
            wordListen.receive(template);
            template.write(outputStream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createWord(InputStream inputStream, OutputStream outputStream, Configure config, Map<String, Object> dataMap, WordListen wordListen) {
        try {
            XWPFTemplate template = XWPFTemplate.compile(inputStream, config).render(dataMap);
            wordListen.receive(template);
            template.write(outputStream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void merge(List<String> fileList,OutputStream outputStream) throws Exception{
        if(fileList==null){
            return;
        }



        NiceXWPFDocument mainDoc = new NiceXWPFDocument(new FileInputStream(fileList.get(0)));

        int fileSize=fileList.size();
        for (int i = 1; i < fileSize; i++) {
            NiceXWPFDocument subDoc = new NiceXWPFDocument(new FileInputStream(fileList.get(i)));
            mainDoc = mainDoc.merge(subDoc);
        }

        mainDoc.write(outputStream);
        mainDoc.close();
        outputStream.close();


    }



    public static void merge(List<String> fileList,String target) throws Exception{

        merge(fileList,new FileOutputStream(new File(target)));

    }


    public static void main(String[] args) {

        try {
            List<String> wordList=new ArrayList<>();
            wordList.add("/Users/upload/zt/report/finalFile/ZTS202200016.docx");
            wordList.add("/Users/upload/zt/report/finalFile/ZTS202200025.docx");

            merge(wordList,"/Users/upload/zt/report/finalFile/1.docx");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }






}
