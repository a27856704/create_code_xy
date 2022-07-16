package ${package};

import ${pubPackage}.common.StringUtil;
import ${pubPackage}.pubInter.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import ${pubPackage}.pubInter.baseVO.AbstractDataVO;
import ${pubPackage}.pubInter.baseVO.AbstractPageVO;

/**
 * @author ${author}
 * @Date ${createTime}
 * @description
 */

public abstract class ApiManageController<DTO extends AbstractDTO
                                              , PageDTO extends AbstractPageDTO
                                              , T extends BaseIdDoMain<KeyType>
                                              , TS extends BaseSearch
                                              , DomainVO extends AbstractDomainVO<KeyType>
                                              , DetailDomainVO extends DomainVO
                                              , DetailVO extends AbstractDataVO<DetailDomainVO>
                                              , ListVO extends AbstractPageVO<DetailDomainVO>
                                                ,KeyType

                > extends RestfulController<DTO, PageDTO,T, TS, DomainVO,DetailDomainVO, DetailVO, ListVO,KeyType> {


    private static boolean debug;


    @Value("${r'${debug}'}")
    public void setDebug(boolean debug) {
        ApiManageController.debug = debug;
    }




}
