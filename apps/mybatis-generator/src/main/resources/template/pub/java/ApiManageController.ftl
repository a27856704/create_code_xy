package ${package};

import ${pubPackage}.common.StringUtil;
import ${pubPackage}.pubInter.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author ${author}
 * @Date ${createTime}
 * @description
 */

public abstract class ApiManageController<DTO extends AbstractDTO
                                              , T extends BaseIdDoMain
                                              , TS extends BaseSearch
                                              , DomainVO extends AbstractDomainVO<String>
    , DataVO extends AbstractDataVO<DomainVO>
        , ListVO extends AbstractPageVO<DomainVO>> extends RestfulController<DTO, T, TS, DomainVO, DataVO, ListVO> {


    private static boolean debug;




    @Value("${r'${debug}'}")
    public void setDebug(boolean debug) {
        ApiManageController.debug = debug;
    }




}
