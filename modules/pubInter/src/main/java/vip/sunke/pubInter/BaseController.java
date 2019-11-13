package vip.sunke.pubInter;


import org.dozer.Mapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import vip.sunke.common.Const;
import vip.sunke.common.FileUtil;
import vip.sunke.common.StringUtil;
import vip.sunke.common.format.*;
import vip.sunke.pubInter.exception.BusinessException;
import vip.sunke.pubInter.exception.BusinessExceptionEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunke
 * @Date 2017/9/20 13:54
 * @description
 */

public class BaseController {


    @Resource(name = "skDozerMapper")
    private Mapper mapper;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

        try {

            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SKCustomDateEditor dateEditor = new SKCustomDateEditor(fmt, true);

            fmt = new SimpleDateFormat("yyyy-MM-dd");
            dateEditor.addDateFormat(fmt);
            fmt = new SimpleDateFormat("yyyy/MM/dd");
            dateEditor.addDateFormat(fmt);

            binder.registerCustomEditor(Date.class, dateEditor);
            binder.registerCustomEditor(int[].class, new IntegerArrayEditor());
            binder.registerCustomEditor(int.class, new IntegerEditor());
            binder.registerCustomEditor(long.class, new LongEditor());
            binder.registerCustomEditor(double.class, new DoubleEditor());
            binder.registerCustomEditor(float.class, new FloatEditor());
            binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());


        } catch (Exception e) {


        }

    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        mapper = mapper;
    }

    public <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        if (sourceList == null)
            return null;


        List<T> destinationList = new ArrayList<T>();


        for (Iterator i$ = sourceList.iterator(); i$.hasNext(); ) {
            Object sourceObject = i$.next();
            Object destinationObject = map(sourceObject, destinationClass);
            destinationList.add((T) destinationObject);
        }
        return destinationList;
    }


    /**
     * 使用dozer技术实现对象的映射.
     *
     * @param source           源对象
     * @param destinationClass 目标对象的类属性
     * @return 目标对象.
     */
    public <T> T map(Object source, Class<T> destinationClass) {
        if (source == null)
            return null;
        return mapper.map(source, destinationClass);
    }


    /**
     * 使用dozer技术实现把源对象数据给目标对象数据.
     *
     * @param source      源对象
     * @param destination 目标对象
     */
    public void map(Object source, Object destination) {
        mapper.map(source, destination);
    }

    /**
     * 使用dozer技术实现对象的映射.
     *
     * @param source           源对象
     * @param destinationClass 目标对象的类属性
     * @param mapId            需要调用的映射id
     * @return 目标对象.
     */
    public <T> T map(Object source, Class<T> destinationClass, String mapId) {
        if (source == null)
            return null;
        return mapper.map(source, destinationClass, mapId);
    }

    /**
     * 使用dozer技术实现把源对象数据给目标对象数据.
     *
     * @param source      源对象
     * @param destination 目标对象
     * @param mapId       需要调用的映射id
     */
    public void map(Object source, Object destination, String mapId) {
        if (source == null)
            return;
        mapper.map(source, destination, mapId);
    }


    public String getUploadFile(MultipartFile file, String frontStr, String[] suffixs) throws BusinessException {

        String fileName = "";
        if (file == null)
            throw new BusinessException(BusinessExceptionEnum.FILE_NULL_ERROR);
        if (file.getSize() == 0)
            throw new BusinessException(BusinessExceptionEnum.FILE_NULL_ERROR);

        String[] fileSplit = null;
        String path = Const.UPLOAD_PATH + File.separator + frontStr;

        fileSplit = file.getOriginalFilename().split("\\.");

        if (fileSplit == null || fileSplit.length < 2) {
            throw new BusinessException(BusinessExceptionEnum.FILE_FORMAT_ERROR);
        }

        String suffix = fileSplit[fileSplit.length - 1];

        if (suffixs != null && suffix.length() > 0) {
            boolean isValid = false;
            for (int i = 0; i < suffixs.length; i++) {
                if (suffixs[i].equalsIgnoreCase(suffix)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid)
                throw new BusinessException(BusinessExceptionEnum.FILE_FORMAT_ERROR);
        }


        fileName = StringUtil.createOrderNo(20) + "." + suffix;
        FileUtil.mkdirs(path);
        File targetFile = new File(path, fileName);

        try {
            file.transferTo(targetFile); // 保存
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return StringUtil.swayStr(frontStr + File.separator + fileName);


    }

    public void getUploadPath(Model model) {
        model.addAttribute("imgPath", Const.UPLOAD_PATH);
    }


}
