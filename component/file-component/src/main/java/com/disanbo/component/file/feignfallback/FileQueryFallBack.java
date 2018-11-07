package com.disanbo.component.file.feignfallback;

import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.common.enums.CommonResponseEnum;
import com.disanbo.component.file.entity.FileVO;
import com.disanbo.component.file.entity.ImageVO;
import com.disanbo.component.file.feign.FileQueryFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangtao
 * @date 2018/10/31 14:50
 */
@Component
public class FileQueryFallBack implements FileQueryFeign {
    /**
     * 通过文件哈希获取文件url
     */
    @Override
    public ResponseVO<FileVO> hash2Url(String fileHash) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }

    /**
     * 通过PDF文件哈希获取PDF图片的url列表
     */
    @Override
    public ResponseVO<List<ImageVO>> pdfHash2Urls(String fileHash) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }

    /**
     * 通过PDF文件哈希和指定页数，将PDF相应页转换为图片，也可用来查询pdf指定页的url信息，用于上传pdf时，由于某些原因导致一些页数没有转换成功时使用
     */
    @Override
    public ResponseVO<ImageVO> pdfPage2Image(String fileHash, String page) {
        return new ResponseVO<>(CommonResponseEnum.exception_feign);
    }
}
