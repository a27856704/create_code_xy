package ${pubPackage}.pubInter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ${pubPackage}.pubInter.common.PageBean;

import java.util.List;

/**
* @author ${author}
* @Date ${createTime}
* @description
*/
@Data
@AllArgsConstructor
@ApiModel("list")
public class DecoratePageList<T> {


    @ApiModelProperty("list")
    private List<T> list;

    private PageBean page;
}
