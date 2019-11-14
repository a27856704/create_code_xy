package vip.sunke.pubInter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import vip.sunke.pubInter.common.PageBean;

import java.util.List;

/**
 * @author sunke
 * @Date 2019/11/13 14:48
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
