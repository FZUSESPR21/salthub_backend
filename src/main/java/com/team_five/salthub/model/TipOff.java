package com.team_five.salthub.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @date 2021/04/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tip_off")
@ApiModel(value="TipOff对象", description="")
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
public class TipOff implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "博客ID")
    @TableField("blog_id")
    private Long blogId;

    @ApiModelProperty(value = "举报次数")
    @TableField("count")
    private Long count;


}
