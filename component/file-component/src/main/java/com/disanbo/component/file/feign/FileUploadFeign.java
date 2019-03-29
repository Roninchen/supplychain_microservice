package com.disanbo.component.file.feign;

import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.file.configuration.FeignMultipartSupportConf;
import com.disanbo.component.file.entity.FileVO;
import com.disanbo.component.file.feignfallback.FileUploadFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author chauncy
 * @date 2018/10/31 14:37
 */
@FeignClient(name = "file-base", path = "/api/file", fallback = FileUploadFallBack.class, configuration = FeignMultipartSupportConf.class)
public interface FileUploadFeign {
    /**
     * 文件上传，支持多文件上传
     */
    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseVO<List<FileVO>> uploads(@RequestPart MultipartFile[] files);

    /**
     * 文件上传，单个文件上传
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseVO<FileVO> upload(@RequestPart(value = "file") MultipartFile file);

    /**
     * PDF文件上传，并且异步转换为图片，支持多PDF文件上传，只支持.pdf格式的文件")
     */
    @PostMapping(value = "/uploads/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseVO<List<FileVO>> uploadsPDF(@RequestPart MultipartFile[] files);

    /**
     * PDF文件上传，单个文件上传，并且异步转换为图片，只支持.pdf格式的文件
     */
    @PostMapping(value = "/upload/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseVO<FileVO> uploadPDF(@RequestPart MultipartFile file);

}
