package vip.sunke.pubInter;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunke
 * @Date 2019/11/13 10:58
 * @description
 */
@Data
public abstract class AbstractDataVO<DomainVO> implements Serializable {

    private DomainVO domain;

}
