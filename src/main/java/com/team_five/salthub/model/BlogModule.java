package com.team_five.salthub.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xydf
 * @since 2021-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("blog_module")
@ApiModel(value="BlogModule对象", description="")
public class BlogModule implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "模块ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "模块名称")
    @TableField("name")
    private String name;


}
