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
                                              , DetailDomainVO extends DomainVO
                                              , DetailVO extends AbstractDataVO<DetailDomainVO>
                                              , ListVO extends AbstractPageVO<DomainVO>> extends RestfulController<DTO, T, TS, DomainVO,DetailDomainVO, DetailVO, ListVO> {


    private static boolean debug;




    @Value("${r'${debug}'}")
    public void setDebug(boolean debug) {
        ApiManageController.debug = debug;
    }




}
