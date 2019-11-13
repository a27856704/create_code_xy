package vip.sunke.common.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import vip.sunke.common.FileUtil;
import vip.sunke.common.PathUtil;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author sunke
 * @Date 2018/12/11 15:07
 * @description
 */

public class PdfUtils {

    public static final String XLS = "application/vnd.ms-excel";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String PDF = "application/pdf";
    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String PNG = "image/png";
    public static final String JPG = "image/jpeg";
    /**
     * 将pdf单页转换为图片
     *
     * @param page         当页对象
     * @param saveFileName 保存的图片名称
     * @param renderer     用于获取BufferedImage
     * @param index        页索引
     * @throws IOException
     */
    public static final float DEFAULT_DPI = 105;
    private static final String IMG_DIR = PathUtil.getCurrentPath() + "/pdf/images";
    private static final String FONT = "simhei.ttf";
    private static final String IMG_SAVE_PATH = "file://" + PathUtil.getCurrentPath() + "/pdf/images";

    /**
     * img to pdf
     *
     * @param imgPathList
     * @param outPdf
     * @return
     */
    public static boolean imgToPdf(List<String> imgPathList, String outPdf) {


        try {
            PDDocument document = new PDDocument();

            for (int i = 0; i < imgPathList.size(); i++) {
                imgToPdf(document, imgPathList.get(i));

            }


            document.save(outPdf);
            //关闭文档
            document.close();

        } catch (Exception e) {

        }


        return false;


    }

    /**
     * 写图片到pdf
     *
     * @param document
     * @param imgPath
     */

    private static void imgToPdf(PDDocument document, String imgPath) {


        PDPageContentStream contentStream = null;
        try {


            // 写PDF文件.
            //  BufferedImage img = ImageIO.read(new File(imgPath));
//          FileOutputStream fos = new FileOutputStream(pdfFile);
            // 创建PDF文档

            // 创建一页
            PDPage blankPage = new PDPage();
            // 添加分页到文档中
            document.addPage(blankPage);
            PDImageXObject pdImageXObject = PDImageXObject.createFromFile(imgPath, document);
            float boxW = blankPage.getBBox().getWidth();
            float boxH = blankPage.getBBox().getHeight();

            // 创建图片


            double pageWidth = pdImageXObject.getWidth();
            double pageHeight = pdImageXObject.getHeight();


            // 创建页面内容输出流
            contentStream = new PDPageContentStream(document, blankPage);
//          contentStream.drawImage(jpeg, 0, 0);
            // 通过内容输出流，画图片对象到当前分页中。不能用drawImage，因为drawImage会直接按原图片的大小输出的。
            contentStream.drawXObject(pdImageXObject, 0, 0, (float) boxW, (float) boxH);


            // 关闭页面输出流
            contentStream.close();
            // 保存PDF文档
            // document.save(pdfPath);
            //关闭文档
            //  document.close();
        } catch (Exception e) {
            e.getStackTrace();
        }


    }

    /**
     * pdf 转化成图片
     *
     * @param pdfPath
     * @param savePathDir
     * @return
     */
    public static List<String> pdfToImg(String pdfPath, String savePathDir) {


        List<String> imgList = new ArrayList<String>();

        FileUtil.mkdirs(savePathDir);


        String fileName = pdfPath.substring(pdfPath.lastIndexOf(File.separator) + 1, pdfPath.length());
        fileName = fileName.substring(0, fileName.lastIndexOf("."));


        InputStream is = null;
        PDDocument pdDocument = null;
        try {
            is = new BufferedInputStream(new FileInputStream(pdfPath));
            //创建pdf文件解析器
            PDFParser parser = new PDFParser(new RandomAccessBuffer(is));
            parser.parse();
            //获取解析后的pdf文档
            pdDocument = parser.getPDDocument();
            //获取pdf渲染器，主要用来后面获取BufferedImage
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            //获取pdf文件总页数
            int pageCount = pdDocument.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                //构造保存文件名称格式
                String saveFileName = savePathDir + File.separator + fileName + "-" + i + ".png";
                //获取当前页对象
                PDPage page = pdDocument.getPage(i);

                //图片转换
                pdfPage2Img(page, saveFileName, renderer, i);

                imgList.add(saveFileName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pdDocument != null) {
                try {
                    pdDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return imgList;
    }

    public static void zoomImage(String src, String dest, int w, int h) throws Exception {

        double wr = 0, hr = 0;
        File srcFile = new File(src);
        File destFile = new File(dest);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr = w * 1.0 / bufImg.getWidth();     //获取缩放比例
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void pdfPage2Img(PDPage page, String saveFileName, PDFRenderer renderer, int index) throws IOException {


        //构造图片
      //  BufferedImage img_temp = renderer.renderImageWithDPI(index, DEFAULT_DPI, ImageType.RGB);

        BufferedImage img_temp=renderer.renderImage(index);


        // BufferedImage imageNew=new BufferedImage(100,100,img_temp.getType());


        //设置图片格式
        Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("png");

        IIOImage iioImage = new IIOImage(img_temp, null, null);


        String temSaveFileName = saveFileName + ".png";

        //将文件写出
        ImageWriter writer = (ImageWriter) it.next();
        ImageOutputStream imageout = ImageIO.createImageOutputStream(new FileOutputStream(saveFileName));
        writer.setOutput(imageout);
        writer.write(iioImage);
       /* try {
            zoomImage(temSaveFileName, saveFileName, 595, 842);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }


    /**
     * 写文本到指定位置
     *
     * @param text
     * @param pdf
     * @param pageNum
     * @param x
     * @param y
     * @return
     */
    public static boolean insertTextToPdf(String text, String pdf, int pageNum, int x, int y) {

        try {
            File file = new File(pdf);
            PDDocument doc = PDDocument.load(file);
            PDPage page = doc.getPage(pageNum);

            PDFont font = PDType1Font.HELVETICA_BOLD;


            //这是选择如何处理流：覆盖、追加
            PDPageContentStream contents = new PDPageContentStream(doc, page, true, true);

            contents.beginText();
            contents.setFont(font, 12);
            contents.moveTextPositionByAmount(x, y);
            contents.drawString(text);

            contents.close();


            doc.save(pdf);


            doc.close();

            return true;


        } catch (Exception e) {


        }


        return false;

    }


    /**
     * 向pdf里写入图片
     *
     * @param img
     * @param pdf
     * @param pageNum
     * @param x
     * @param y
     * @return
     */
    public static boolean insertImgToPdf(String img, String pdf, int pageNum, int x, int y) {

        try {
            File file = new File(pdf);
            PDDocument doc = PDDocument.load(file);
            PDPage page = doc.getPage(pageNum);

            PDImageXObject pdImage = PDImageXObject.createFromFile(img, doc);


            //这是选择如何处理流：覆盖、追加
            PDPageContentStream contents = new PDPageContentStream(doc, page, true, true);
            //控制图片的大小


            // System.out.println(pdImage.getHeight());
            //System.out.println(pdImage.getWidth());

            // Drawing the image in the PDF document
            contents.drawImage(pdImage, x, y, pdImage.getWidth(), pdImage.getHeight());


            // Closing the PDPageContentStream object
            contents.close();

            // Saving the document
            doc.save(pdf);

            // Closing the document
            doc.close();

            return true;


        } catch (Exception e) {

        }


        return false;

    }


    /**
     * word to pdf
     *
     * @param wordFile word 文件路径
     * @param outPdf   outPdf
     * @return
     */
    public static boolean docToPdf(String wordFile, String outPdf) {


        try {


            if (wordFile.lastIndexOf(".docx") > 0) {
                htmlToPdf(docx2Html(wordFile, null), outPdf);

            } else {
                htmlToPdf(doc2Html(wordFile, null), outPdf);


            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;

    }


    public static void htmlToPdf(String content, String dest) throws IOException, DocumentException, com.lowagie.text.DocumentException {


        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
        render.getSharedContext().setBaseURL(IMG_SAVE_PATH);
        render.layout();
        render.createPDF(new FileOutputStream(dest));


    }


    public static String docx2Html(String fileName, String outPutFile) throws Exception {

        FileUtil.mkdirs(IMG_DIR);
        FileInputStream fis = new FileInputStream(fileName);

        XWPFDocument xdoc = new XWPFDocument(fis);

        XHTMLOptions options = XHTMLOptions.create();
        File imageFolder = new File(IMG_DIR);
        options.setExtractor(new FileImageExtractor(imageFolder));
        options.URIResolver(new FileURIResolver(imageFolder));

        String temp = new Date().getTime() + ".html";


        OutputStream out = new FileOutputStream(new File(temp));
        XHTMLConverter.getInstance().convert(xdoc, out, options);

        String result = writeFile(readInFile(temp), outPutFile);

        FileUtil.deleteFile(temp);

        return result;


    }


    //word 转 html
    public static String doc2Html(String fileName, String outPutFile)
            throws Exception {

        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));


        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content,
                                      PictureType pictureType, String suggestedName,
                                      float widthInches, float heightInches) {
                return IMG_SAVE_PATH + "/" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        //save pictures
        List pics = wordDocument.getPicturesTable().getAllPictures();

        FileUtil.mkdirs(IMG_DIR);


        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);

                try {
                    pic.writeImageContent(new FileOutputStream(IMG_DIR + "/"
                            + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);


        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "HTML");
        serializer.transform(domSource, streamResult);
        out.close();
        return writeFile(new String(out.toByteArray()), outPutFile);
    }

    //输出html文件
    public static String writeFile(String content, String path) {


        FileOutputStream fos = null;

        BufferedWriter bw = null;
        org.jsoup.nodes.Document doc = Jsoup.parse(content);


        Element divElement = doc.createElement("div");
        divElement.attr("style", "display:inline-block;font-family:'SimHei';");


        divElement.append("<div style=\"text-align:left\">" + doc.body().html() + "</div>");

        doc.body().empty();

        divElement.appendTo(doc.body());


        doc.select("meta").removeAttr("name");
        doc.select("meta").attr("content", "text/html; charset=UTF-8");
        doc.select("meta").attr("http-equiv", "Content-Type");
        doc.select("meta").html("&nbsp;");
        doc.select("p1").attr("style", "font-family:'SimHei'");

        Elements elements = doc.select("span");

        if (elements != null) {
            for (Element element : elements) {
                element.attr("style", element.attr("style") + " font-family:'SimHei'");
            }
        }

        elements = doc.select("p");
        if (elements != null) {
            for (Element element : elements) {
                element.attr("style", element.attr("style").replace("text-align:right", "text-align:left"));
            }
        }


        // doc.select("span").attr("style", "font-family:'SimHei'");


        doc.select("p2").attr("style", "font-family:'SimHei'");
        doc.select("br").html("&nbsp;");


        //  doc.select("body").append("<img src='"+IMG_DIR+"/logo.png"+"' class='logo'>");
        //  doc.select(".logo").attr("style","width:200px;");


        doc.select("img").html("&nbsp;");


        //doc.select("html").before("&lt;!DOCTYPE html [&lt;!ENTITY nbsp \" \"&gt;]&gt;");


        content = doc.html();


        // return content;
        return "<!DOCTYPE html [<!ENTITY nbsp \" \">]>" + content;


        /*try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }*/
    }

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


}
