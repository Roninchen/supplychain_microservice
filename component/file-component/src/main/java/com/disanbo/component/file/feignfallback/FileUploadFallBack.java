package com.disanbo.component.file.feignfallback;

import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.common.enums.CommonResponseEnum;
import com.disanbo.component.file.entity.FileVO;
import com.disanbo.component.file.feign.FileUploadFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangtao
 * @date 2018/10/31 14:44
 */
@Component
public class FileUploadFallBack implements FileUploadFeign {
    /**
     * 文件上传，支持多文件上传
     */
    @Override
    public ResponseVO<List<FileVO>> uploads(MultipartFile[] files) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }

    /**
     * 文件上传，单个文件上传
     */
    @Override
    public ResponseVO<FileVO> upload(MultipartFile file) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }

    /**
     * PDF文件上传，并且异步转换为图片，支持多PDF文件上传，只支持.pdf格式的文件")
     */
    @Override
    public ResponseVO<List<FileVO>> uploadsPDF(MultipartFile[] files) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }

    /**
     * PDF文件上传，单个文件上传，并且异步转换为图片，只支持.pdf格式的文件
     */
    @Override
    public ResponseVO<FileVO> uploadPDF(MultipartFile file) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }
}
