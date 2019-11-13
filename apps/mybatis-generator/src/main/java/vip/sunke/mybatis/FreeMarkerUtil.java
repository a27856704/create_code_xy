package vip.sunke.mybatis;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.util.Locale;
import java.util.Map;

/**
 * @author sunke
 * @Date 2019-04-29 14:07
 * @description
 */

public class FreeMarkerUtil {

    private static FreeMarkerUtil fk;

    private static Configuration cfg;

    private FreeMarkerUtil() {


    }


    public static FreeMarkerUtil getInstance(String freemarkerVersionNo, String templatePath) {

        if (null == fk) {
            cfg = new Configuration(new Version(freemarkerVersionNo));
            cfg.setClassForTemplateLoading(FreeMarkerUtil.class, templatePath);
           // cfg.setTemplateLoader(new ClassTemplateLoader(FreeMarkerUtil.class, templatePath));

            fk = new FreeMarkerUtil();
        }
        return fk;

    }


    /**
     * @param ftlName  模块名称
     * @param root     数据
     * @param outFile  输出文件
     * @param filePath 输出目录
     * @param ftlPath  模块目录
     * @throws Exception
     */

    public void printFile(String ftlName, Map<String, Object> root, String outFile, String filePath, String ftlPath) throws Exception {
        try {

            File file = new File(filePath + File.separator + outFile);
            if (!file.getParentFile().exists()) {                //判断有没有父路径，就是判断文件整个路径是否存在
                file.getParentFile().mkdirs();                //不存在就全部创建
            }
            if (file.exists()) {
                file.delete();
            }


            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);                    //模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {

        } catch (IOException e) {

        } finally {

        }
    }

    public Template getTemplate(String ftlName, String ftlPath) throws Exception {
        try {
            //通过Freemaker的Configuration读取相应的ftl
            cfg.setEncoding(Locale.CHINA, "utf-8");
            cfg.setDirectoryForTemplateLoading(new File(ftlPath));        //设定去哪里读取相应的ftl模板文件
            Template temp = cfg.getTemplate(ftlName);                                                //在模板文件目录中找到名称为name的文件
            return temp;
        } catch (IOException e) {

            try {
                cfg.setClassForTemplateLoading(this.getClass(), ftlPath);
              //  cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), ftlPath));
                Template temp = cfg.getTemplate(ftlName);
                return temp;
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }
        return null;
    }


    public void print(String ftlName, Map<String, Object> root, String ftlPath, OutputStream outputStream) throws Exception {
        try {
            Template temp = getTemplate(ftlName, ftlPath);        //通过Template可以将模板文件输出到相应的流
            temp.process(root, new PrintWriter(outputStream));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void print(String ftlName, Map<String, Object> root, String ftlPath) throws Exception {
        print(ftlName, root, ftlPath, System.out);
    }


    /**
     * @param fltFile
     * @param templatePath
     * @param dataMap
     * @return
     */
    public String geneFileStr(String fltFile, String templatePath, Map<String, Object> dataMap) {


        try {

            Template template = cfg.getTemplate(fltFile, "utf-8");

            StringWriter out = new StringWriter();
            template.process(dataMap, out);
            out.flush();
            out.close();
            return out.getBuffer().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
