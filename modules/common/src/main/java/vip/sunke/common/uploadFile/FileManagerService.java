package vip.sunke.common.uploadFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author sunke
 * @Date 2019-07-19 09:18
 * @description
 */

public interface FileManagerService {

    /**
     * 保存切片
     *
     * @param fileInfo
     * @param file
     * @throws Exception
     */
    void saveMultiBurstFileToDir(MultiFileInfo fileInfo, MultipartFile file) throws Exception;

    /**
     * 保存单一文件
     *
     * @param fileInfo
     * @param file
     * @throws Exception
     */

    String saveSingleFileToDir(MultiFileInfo fileInfo, MultipartFile file) throws Exception;

    File generateDirPathForCurrFile(MultiFileInfo fileInfo, String flag, String realFileName) throws Exception;

    //文件合并
    String multiMergingChunks(MultiFileInfo fileInfo) throws Exception;


}

