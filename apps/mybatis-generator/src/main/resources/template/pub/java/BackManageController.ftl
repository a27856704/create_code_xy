package ${package};
import ${pubPackage}.common.FileUtil;
import ${pubPackage}.common.StringUtil;
import ${pubPackage}.common.uploadfile.FileManagerService;
import ${pubPackage}.common.uploadfile.MultiFileInfo;
import ${pubPackage}.common.uploadfile.MultiFileUploadUtils;
import ${pubPackage}.pubInter.BackController;
import ${pubPackage}.pubInter.BaseIdDoMain;
import ${pubPackage}.pubInter.BaseSearch;
import ${pubPackage}.pubInter.IBaseService;
import ${pubPackage}.pubInter.exception.BusinessException;
import ${pubPackage}.pubInter.exception.SkException;
import ${pubPackage}.web.common.SkMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @author ${author}
 * @Date ${createTime}
 * @description
 */
@Controller
public class BackManageController<T extends BaseIdDoMain<KeyType>, TS extends BaseSearch, KeyType> extends BackController<T, TS, KeyType> {


    private static boolean debug;

    private static String imgWebSite;
    private static String uploadFilePath;


    private static String upFileConfig;
    public static String getUpFileConfig() {
        return upFileConfig;
    }

    @Value("${r'${file.upload.uploadConfig}'}")
    public void setUpFileConfig(String upFileConfig) {
        BackManageController.upFileConfig = upFileConfig;
    }



    @Autowired
    private FileManagerService fileManagerService;

    public static String getUploadFilePath() {
        return uploadFilePath;
    }

    @Value("${r'${file.upload.root}'}")
    public void setUploadFilePath(String uploadFilePath) {
        BackManageController.uploadFilePath = uploadFilePath;
    }

    public static String getImgWebSite() {
        return imgWebSite;
    }

    @Value("${r'${file.upload.website}'}")
    public void setImgWebSite(String imgWebSite) {
        BackManageController.imgWebSite = imgWebSite;
    }

    public static String getSplitMenuUrl(String menuUrl) {

        if (StringUtil.isEmpty(menuUrl))
            return "";

        return menuUrl.split(";")[0];


    }

    public static boolean isImgPrefix(String imgs) {


        if (StringUtil.isEmpty(imgs))
            return false;


        for (String imgPrefix : Const.IMG_TYPE) {

            if (imgs.lastIndexOf("." + imgPrefix) >= 0)
                return true;


        }

        return false;


    }

    public static String getMenu(String menuUrl) {

        try {
            String url = getSplitMenuUrl(menuUrl);

            String[] urlArr = url.split("/");

            return urlArr[1];
        } catch (Exception e) {
            return "";
        }


    }

    public static String getMenuUrl(String menuUrl) {
        try {
            String url = getSplitMenuUrl(menuUrl);

            String[] urlArr = url.split("/");

            return urlArr[2] + "/" + urlArr[3];
        } catch (Exception e) {
            return "";
        }


    }

    public static boolean contains(List<String> values, String rightsUrl) {

        if (debug || true)
            return true;

        if (values == null || values.size() == 0 || StringUtil.isEmpty(rightsUrl))
            return false;

        for (String value : values) {
            if (rightsUrl.toLowerCase().contains(value.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static String containsValue(String values, String currValues) {
        if (StringUtil.isEmpty(values) || StringUtil.isEmpty(currValues))
            return currValues;

        String[] valueArr = values.split("\\,");

        if (valueArr == null || valueArr.length == 0)
            return currValues;


        String[] oneValueArr = null;
        for (String value : valueArr) {
            if (value == null || "".equalsIgnoreCase(value))
                continue;
            oneValueArr = value.split("@");

            if (currValues.equalsIgnoreCase(oneValueArr[0])) {
                if (oneValueArr.length > 1) {
                    return oneValueArr[1];
                } else {
                    return oneValueArr[0];
                }
            }

        }
        return currValues;
    }

    @Override
    public IBaseService<T, TS, KeyType> getBaseService() throws SkException {
        return null;
    }

    @Override
    public String getBaseRoute() throws SkException {
        return null;
    }

    @Override
    public String getBaseView() throws SkException {
        return null;
    }

    @Value("${r'${debug}'}")
    public void setDebug(boolean debug) {
        BackManageController.debug = debug;
    }

    /*public Admin getAdmin(HttpSession session) throws BusinessException {

        Admin admin = (Admin) session.getAttribute(Const.ADMIN_SESSION);

        if (admin == null)
            throw new BusinessException(BusinessExceptionEnum.OBJ_NULL_ERROR);

        return admin;


    }*/


    /**
    * 上传多个文件
    *
    * @param uploadFlag
    * @param files
    * @return
    * @throws BusinessException
    */
    @PostMapping("/postUploads")
    @ResponseBody
    public SkMap postUploads(
            @RequestParam(required = false, defaultValue = "edit", value = "uploadFlag") String uploadFlag,
            @RequestParam(value = "files", required = false) MultipartFile[] files) throws BusinessException {
        String path = uploadFlag;
        String resultPath = "";
        int size = files.length;
        for (MultipartFile file : files) {
            resultPath = resultPath + getUploadFile(file, path, Const.IMG_TYPE) + ",";
        }
        return SkMap.ok("path", resultPath).set("imgWebSite", BackManageController.imgWebSite);
    }



    /**
     * * 文件上传
     * * @param fileInfo:文件参数实体类
     * * @param file 附件字节码文件
     * * @return 返回处理结果，请求头200:成功,500:失败
     * * @throws Exception
     */
    @PostMapping(value = "webUploader")
    @ResponseBody
    public SkMap webUploader(MultiFileInfo fileInfo, @RequestParam(required = false, value = "file")
            MultipartFile
            file, HttpServletResponse response) throws Exception {


        try {

            if (file != null && !file.isEmpty()) {


                if (MultiFileUploadUtils.checkMultiFileParameter(fileInfo)) { //切片上传
                    fileManagerService.saveMultiBurstFileToDir(fileInfo, file);
                } else if (MultiFileUploadUtils.checkSingleFileParameter(fileInfo)) {//单文件整体上传
                    fileManagerService.saveSingleFileToDir(fileInfo, file);
                } else {
                    throw new BusinessException("文件上传参数不合法");
                }
            } else {
                throw new BusinessException("文件上传附件字节流内容为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }

        return SkMap.ok().set("fileId", fileInfo.getId()).set("fileInfo", fileInfo);

    }

    @Override
    public void allPageExtend(Model model, HttpServletRequest request, HttpSession session) throws SkException {
        super.allPageExtend(model, request, session);
        model.addAttribute("imgWebSite", getImgWebSite());
    }

    /**
     * 合并文件
     *
     * @param fileInfo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/mergingChunks")
    @ResponseBody
    public SkMap mergingChunks(MultiFileInfo fileInfo) throws Exception {


        return SkMap.ok("filePath", fileManagerService.multiMergingChunks(fileInfo))
                .set("fileId", fileInfo.getId())
                .set("imgWebSite", BackManageController.getImgWebSite());
    }


    /**
     * 删除文件
     *
     * @param fileName 不带域名
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delFile")
    @ResponseBody
    public SkMap delFile(String fileName) throws Exception {

        FileUtil.deleteFile(getUploadFilePath() + File.separator + fileName);

        return SkMap.ok();
    }

}
