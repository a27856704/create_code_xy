package ${pubPackage}.pubInter;

import lombok.Data;
import ${pubPackage}.pubInter.common.PageBean;

import java.io.Serializable;
import java.util.List;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/
@Data
public abstract class AbstractPageVO<DomainVO> implements Serializable {


    private PageBean page;

    private List<DomainVO> list;



}
