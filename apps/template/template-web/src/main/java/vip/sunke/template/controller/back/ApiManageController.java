package vip.sunke.template.controller.back;

import org.springframework.beans.factory.annotation.Value;
import vip.sunke.pubInter.*;

/**
 * @author sunke
 * @Date 2019-12-09 14:43:58
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




    @Value("${debug}")
    public void setDebug(boolean debug) {
        ApiManageController.debug = debug;
    }




}
