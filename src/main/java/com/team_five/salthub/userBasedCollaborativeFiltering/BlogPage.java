package com.team_five.salthub.userBasedCollaborativeFiltering;

import com.team_five.salthub.controller.BlogController;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 带有分页的博客数据
 *
 * @date 2021/05/04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Blog分页对象", description="")
public class BlogPage implements Serializable {

    private static final long serialVersionUID=1L;

    public BlogPage(long totalSize, long currentPage, List<Object> objectList) {
        this.totalPage = (totalSize / BlogController.PAGE_SIZE);
        this.totalPage = ((totalSize % BlogController.PAGE_SIZE) != 0) ? (this.totalPage + 1) : this.totalPage;
        this.currentPage = currentPage;
        this.blogList = objectList;
    }

    @ApiModelProperty(value = "一共页数")
    private long totalPage;

    @ApiModelProperty(value = "当前页数")
    private long currentPage;

    @ApiModelProperty(value = "博客列表")
    private List<Object> blogList;
}
