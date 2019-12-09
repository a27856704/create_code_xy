package vip.sunke.template.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import vip.sunke.pubInter.AbstractDomainVO;

import java.util.Date;

/**
*    @author sunke
*    @Date 2019-12-09 14:43:58
*    @description NewsDomainVO      新闻
*/

@Data
@Accessors(chain = true)
@ApiModel("新闻")
public class NewsDomainVO extends AbstractDomainVO<String> {



    /**
     * 标题
    */

    @ApiModelProperty(value = "标题")
    private String title;


    /**
     * 类型
     * 1:行业,2:专业,3:科技,4:社会
    */

    @ApiModelProperty(value = "类型 1:行业,2:专业,3:科技,4:社会")
    private Integer type;
    @ApiModelProperty(value = "类型")
    private String typeDesc;


    /**
     * 状态
     * 1:待审,2:通过,-2:拒绝,3:发布
    */

    @ApiModelProperty(value = "状态 1:待审,2:通过,-2:拒绝,3:发布")
    private Integer status;
    @ApiModelProperty(value = "状态")
    private String statusDesc;


    /**
     * 作者
     * 1:张三,2:李四,3:王五,4:赵六
    */

    @ApiModelProperty(value = "作者 1:张三,2:李四,3:王五,4:赵六")
    private Integer author;
    @ApiModelProperty(value = "作者")
    private String authorDesc;


    /**
     * 是否显示
     * 1:显示,0:隐藏
    */

    @ApiModelProperty(value = "是否显示 1:显示,0:隐藏")
    private Integer show;
    @ApiModelProperty(value = "是否显示")
    private String showDesc;


    /**
     * 发送时间
    */

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;


    /**
     * 添加时间
    */

    @ApiModelProperty(value = "添加时间")
    private Date addTime;


}