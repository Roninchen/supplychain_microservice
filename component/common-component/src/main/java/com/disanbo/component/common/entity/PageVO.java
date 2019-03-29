package com.disanbo.component.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chauncy
 * @date 2018/10/29 11:36
 */
@Data
@ApiModel("分页信息参数")
public class PageVO<T> {
    @ApiModelProperty(value = "当前页号")
    private Integer page;
    @ApiModelProperty(value = "每页行数")
    private Integer size;
    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
    @ApiModelProperty(value = "总行数")
    private Integer totalSize;
    @ApiModelProperty(value = "是否有下一页，0：没有，1：有")
    private Integer isMore;
    @ApiModelProperty(value = "分页结果数据")
    private List<T> data;

    /**
     * 适用于list数量不多的情况
     */
    public void init(List<T> list) {
        this.totalSize = (list == null || list.isEmpty()) ? 0 : list.size();
        this.totalPage = (totalSize % size > 0) ? (totalSize / size + 1) : (totalSize / size);
        this.isMore = (page < totalPage) ? 1 : 0;
        if (totalSize != 0) {
            long begin = (page - 1) * size;
            this.data = list.stream().skip(begin).limit(size).collect(Collectors.toList());
        }
    }
}
