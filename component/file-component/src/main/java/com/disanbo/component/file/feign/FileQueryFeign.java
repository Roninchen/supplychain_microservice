package com.disanbo.component.file.feign;

import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.file.entity.FileVO;
import com.disanbo.component.file.entity.ImageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wangtao
 * @date 2018/10/31 14:47
 */
@FeignClient(name = "file-base", path = "/api/file")
public interface FileQueryFeign {
    /**
     * 通过文件哈希获取文件url
     */
    @GetMapping("/{fileHash}")
    ResponseVO<FileVO> hash2Url(@PathVariable String fileHash);

    /**
     * 通过PDF文件哈希获取PDF图片的url列表
     */
    @GetMapping("/image/{fileHash}")
    ResponseVO<List<ImageVO>> pdfHash2Urls(@PathVariable String fileHash);

    /**
     * 通过PDF文件哈希和指定页数，将PDF相应页转换为图片，也可用来查询pdf指定页的url信息，用于上传pdf时，由于某些原因导致一些页数没有转换成功时使用
     */
    @GetMapping("/2image/{fileHash}")
    public ResponseVO<ImageVO> pdfPage2Image(@PathVariable String fileHash, @RequestParam String page);
}
