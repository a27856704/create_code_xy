package vip.sunke.common.uploadFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.sunke.common.FileUtil;
import vip.sunke.common.SHA256Util;
import vip.sunke.common.StringUtil;
import vip.sunke.common.YXDate;

import java.io.File;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunke
 * @Date 2019-07-19 09:19
 * @description
 */
@Service
public class FileManagerServiceImpl implements FileManagerService {


    @Value("${file.upload.tempRoot}")
    private String tempWorkPath;//分片临时文件存放目录
    @Value("${file.upload.root}")
    private String saveFilePath;//文件存放目录

    private ReentrantLock fileTempLock = new ReentrantLock();

    @Override
    public void saveMultiBurstFileToDir(MultiFileInfo fileInfo, MultipartFile file) throws Exception {

        this.checkBaseDir(tempWorkPath);

        File tempFile = this.generateDirPathForCurrFile(fileInfo, "chunks", null);


        MultiFileUploadUtils.spaceFileWriter(file, tempFile, fileInfo);


    }

    @Override
    public String saveSingleFileToDir(MultiFileInfo fileInfo, MultipartFile file) throws Exception {

        this.checkBaseDir(tempWorkPath);
        String filePath = createTempFileName(fileInfo);

        File targetFile = this.generateDirPathForCurrFile(fileInfo, "single", filePath);

        MultiFileUploadUtils.saveFile2DirPath(file, targetFile);

        return filePath;


      /*  this.checkBaseDir(saveFilePath);

        String filePath = realCreateFileName(fileInfo);

        File targetFile = this.generateDirPathForCurrFile(fileInfo, "single", saveFilePath + File.separator + filePath);

        MultiFileUploadUtils.saveFile2DirPath(file, targetFile);

        return filePath;*/
    }

    /**
     * 创建某个文件切块
     *
     * @param fileInfo
     * @return
     */
    private String createTempFileName(MultiFileInfo fileInfo) {
        String fileName = fileInfo.getName();
        String lastModifiedDate = fileInfo.getLastModifiedDate();
        long fileSize = fileInfo.getSize();
        String id = fileInfo.getId();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String fileNameSource = fileSize + "_" + fileName + id + lastModifiedDate;
        return tempWorkPath + "/" + SHA256Util.getSHA256StrJava(fileNameSource) + extName + ".temp";

    }


    private String realCreateFileName(MultiFileInfo fileInfo) {

        String fileName = fileInfo.getName();
        String lastModifiedDate = fileInfo.getLastModifiedDate();
        long fileSize = fileInfo.getSize();
        String id = fileInfo.getId();
        String fileNameSource = YXDate.getFormatDate2String(new Date(), "yyyyMMddHHmmss") + "_" + fileSize + "_" + fileName + id + lastModifiedDate;
        return SHA256Util.getSHA256StrJava(fileNameSource) + "." + fileInfo.getExt();
    }


    @Override
    public synchronized File generateDirPathForCurrFile(MultiFileInfo fileInfo, String flag, String realFileName) throws Exception {

        if ("single".equals(flag)) {

            File targetFile = new File(realFileName);
            return targetFile;
        } else if ("chunks".equals(flag)) {


            String fileDirName = createTempFileName(fileInfo);


            File tempFile = new File(fileDirName);//禁用FileInfo.exists()类, 防止缓存导致并发问题

            if (!(tempFile.exists() && tempFile.isFile())) {
                fileTempLock.lock();//上锁
                if (!(tempFile.exists() && tempFile.isFile())) {
                    MultiFileUploadUtils.readySpaceFile(fileInfo, tempFile);
                }
                fileTempLock.unlock();//释放锁
            }
            tempFile = new File(fileDirName);
            return tempFile;
        } else {
            throw new Exception("目标文件生成失败");
        }
    }

    @Override
    public String multiMergingChunks(MultiFileInfo fileInfo) throws Exception {


        String fileDirName = createTempFileName(fileInfo);

        File tempFile = new File(fileDirName);

        if (tempFile.exists() && tempFile.isFile()) {

            checkBaseDir(saveFilePath);


            String fileName=realCreateFileName(fileInfo);
            if(!StringUtil.isEmpty(fileInfo.getSavePath())){
                FileUtil.mkdirs(saveFilePath + File.separator+fileInfo.getSavePath());
                fileName=fileInfo.getSavePath()+File.separator+fileName;
            }


            File targetFile = new File(saveFilePath + File.separator + fileName);

          //  System.out.println(targetFile.getAbsolutePath());
            if (tempFile.renameTo(targetFile)) {
                return fileName;
            } else {
                System.out.println("文件重命名失败!");
                throw new Exception("临时文件重命名失败");
            }


        } else {
            return "";
        }

    }

    public void checkBaseDir(String baseDir) throws Exception {
        File file = new File(baseDir);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
}
