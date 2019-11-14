package vip.sunke.pubInter;

import lombok.Data;
import vip.sunke.pubInter.common.PageBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunke
 * @Date 2019/11/13 14:22
 * @description  分类
 */
@Data
public abstract class AbstractPageVO<VO> implements Serializable {


    private PageBean page;

    private List<VO> list;



}
