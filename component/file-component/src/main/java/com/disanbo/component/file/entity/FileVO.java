package com.disanbo.component.file.entity;

import lombok.Data;

/**
 * @author wangtao
 * @date 2018/10/26 14:17
 */
@Data
public class FileVO {
    private String fileName;
    private String fileType;
    private String fileUrl;
    private String fileHash;
    private String createTime;
}
