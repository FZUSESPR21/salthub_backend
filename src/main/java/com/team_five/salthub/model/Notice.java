package com.team_five.salthub.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("notice")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Notice对象", description="")
public class Notice implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "公告ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "公告标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "公告内容")
    @TableField("content")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    @TableField("release_time")
    private Date releaseTime;

    @ApiModelProperty(value = "接收用户, ***则为全部用户")
    @TableField(value = "account_name", select = true)
    private String accountName;


}
