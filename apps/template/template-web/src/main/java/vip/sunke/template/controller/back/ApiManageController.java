package vip.sunke.template.controller.back;

import org.springframework.beans.factory.annotation.Value;
import vip.sunke.pubInter.*;

/**
 * @author sunke
 * @Date 2019-11-14 22:15:43
 * @description
 */

public abstract class ApiManageController<DTO extends AbstractDTO
                                              , T extends BaseIdDoMain
                                              , TS extends BaseSearch
                                              , DomainVO extends AbstractDomainVO<String>
    , DataVO extends AbstractDataVO<DomainVO>
        , ListVO extends AbstractPageVO<DomainVO>> extends RestfulController<DTO, T, TS, DomainVO, DataVO, ListVO> {


    private static boolean debug;




    @Value("${debug}")
    public void setDebug(boolean debug) {
        ApiManageController.debug = debug;
    }




}
