package com.disanbo.component.file.entity;

import lombok.Data;

/**
 * @author chauncy
 * @date 2018/10/26 16:52
 */
@Data
public class ImageVO {
    private String fileName;
    private String fileType;
    private Integer page;
    private String fileUrl;
}
