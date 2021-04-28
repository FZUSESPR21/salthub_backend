package com.team_five.salthub.model;

import com.team_five.salthub.exception.BaseException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一响应类
 *
 * @date 2021/04/26
 */
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ResponseMessage对象", description="统一响应类")
@Data
@AllArgsConstructor
public class ResponseMessage {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "响应码")
    private Integer code;
    @ApiModelProperty(value = "响应信息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private Object data;

    public static ResponseMessage success(Object data) {
        return new ResponseMessage(200, "OK", data);
    }

    public static ResponseMessage success() {
        return new ResponseMessage(200, "OK", null);
    }

    public static ResponseMessage fail(BaseException e) {
        return new ResponseMessage(e.getCode(), e.getMessage(), null);
    }
}
