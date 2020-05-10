package top.fotg.eduorder.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.awt.*;

@Data
public class OrderQuery {

    @ApiModelProperty(value = "订单商品名称,模糊查询")
    private String courseTitle;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
