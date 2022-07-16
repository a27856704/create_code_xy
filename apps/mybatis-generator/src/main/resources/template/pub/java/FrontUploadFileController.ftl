package ${pubPackage}.pubInter;


import ${pubPackage}.common.Const;
import ${pubPackage}.pubInter.UploadFileVO;
import ${pubPackage}.pubInter.exception.SkException;
import ${pubPackage}.web.common.SkJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author ${author}
 * @Date ${createTime}
 * @description 前台上传文件
 */
@RestController
@Api(tags = "上传文件", description = "上传文件接口")
public class FrontUploadFileController extends FrontController {


    private static String fileBaseUrl;


    @Value("${r'${file.upload.website}'}")
    public void setFileBaseUrl(String fileBaseUrl) {
         FrontUploadFileController.fileBaseUrl = fileBaseUrl;
    }


    /**
     * 上传文件
     *
     * @param file
     * @param type
     * @return
     * @throws SkException
     */
    @ApiOperation(value = "上传图片", notes = "上传图片接口", httpMethod = "POST")
    @PostMapping("/uploadFile")
    @ResponseBody
    public SkJsonResult<UploadFileVO> uploadFile(@RequestParam("file") MultipartFile file, @ApiParam("文件目录") @RequestParam(value = "type", defaultValue = "evaluation", required = false) String type) throws SkException {

        UploadFileVO vo = new UploadFileVO();
        vo.setWebsite(FrontUploadFileController.fileBaseUrl);
        vo.setImg(getUploadFile(file, type + "/", Const.IMG_TYPE));
        return SkJsonResult.ok().setData(vo);

    }


}