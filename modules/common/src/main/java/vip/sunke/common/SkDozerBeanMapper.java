package vip.sunke.common;


import org.dozer.DozerBeanMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunke
 * @Date 2017/6/9 14:48
 * @description
 */

public class SkDozerBeanMapper extends DozerBeanMapper {

    private String mapperFilePath;


    public String getMapperFilePath() {
        return mapperFilePath;
    }

    public void setMapperFilePath(String mapperFilePath) {
        this.mapperFilePath = mapperFilePath;


        setMappingFiles();

    }


    public void setMappingFiles() {


        List<String> fileNameList = new ArrayList<String>();

        String fontFilePath = FileUtil.getClassPath() + "classes" + File.separator;

        File dirFile = new File(fontFilePath + this.mapperFilePath);
        FileUtil.getFolderFiles(dirFile, "xml", fileNameList, fontFilePath);

        if (fileNameList == null || fileNameList.size() == 0) {

            return;
        }

       /* List<String> mappingFiles = new ArrayList<String>();
        File file = null;
        for (String filePath : files) {
            if (filePath.lastIndexOf(".xml") >= 0)
                mappingFiles.add(this.getMapperFilePath() + filePath);


        }
*/

        super.setMappingFiles(fileNameList);
    }
}
