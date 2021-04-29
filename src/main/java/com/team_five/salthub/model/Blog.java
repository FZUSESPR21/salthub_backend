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

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 博客类
 * </p>
 *
 * @date 2021/04/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("blog")
@ApiModel(value="Blog对象", description="")
@AllArgsConstructor
public class Blog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "博客ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "模块ID")
    @TableField("module_id")
    private Long moduleId;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "发布时间")
    @TableField("release_time")
    private Date releaseTime;

    @ApiModelProperty(value = "点赞数")
    @TableField("like_number")
    private Long likeNumber;

    @ApiModelProperty(value = "收藏数")
    @TableField("collection_number")
    private Long collectionNumber;

    @ApiModelProperty(value = "状态")
    @TableField("state")
    private Integer state;


}
