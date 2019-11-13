package ${pubPackage}.pubInter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author ${author}
* @Date ${createTime}
* @description Source search都继承此类
*/


@Data
public abstract class AbstractPageDTO extends AbstractDTO {

    @ApiModelProperty(value = "页码")
    private int pageNumber=1;

    @ApiModelProperty(value = "每页数量")
    private int pageSize=15;

}
