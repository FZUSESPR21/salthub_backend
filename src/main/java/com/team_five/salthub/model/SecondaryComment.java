package com.team_five.salthub.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @date 2021/04/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("secondary_comment")
@ApiModel(value="SecondaryComment对象", description="")
public class SecondaryComment implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "评论ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "被评论评论ID")
    @TableField("comment_id")
    private Long commentId;

    @ApiModelProperty(value = "发布者姓名")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "被评论用户")
    @TableField("account_name")
    private String accountName;

    @ApiModelProperty(value = "评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "发布时间")
    @TableField("release_time")
    private Date releaseTime;


}
