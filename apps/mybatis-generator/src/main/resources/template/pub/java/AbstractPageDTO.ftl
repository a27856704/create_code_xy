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


    @ApiModelProperty(value = "排序字段", hidden = true)
    private String orderBy;// 排序字段
    @ApiModelProperty(value = "倒序，顺序", hidden = true)
    private String orderDesc = "desc";// 倒序，顺序

    @ApiModelProperty("开始创建时间")
    private Date createTimeStart;

    @ApiModelProperty("结束创建时间")
    private Date createTimeEnd;

    @ApiModelProperty(value = "主键IDs")
    private List idIn;


    //是否要填充其它数据
    private int fullOtherData = 1;


    /**
     * 用来保持是否附加其实数据
     */
    @ApiModelProperty(value = "用来保持附加其实数据")
    private Set<String> fullConfigSet;

    @ApiModelProperty(value = "用来保持要删除的数据key")
    private Set<String> unFullConfigSet;

    @ApiModelProperty(value = "是否删除 1:是:Y,0:否:N")
    private Integer delFlagEq=new Integer(0);




}
