package com.cddysq.auditsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cddysq.auditsystem.vo.DocumentVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:59
 * @since 1.0.0
 **/
public interface DocumentService {

    IPage<DocumentVo> queryAllPageList(Long userId, int page, int size);

    /**
     * 审核文件
     *
     * @param fileId 文件ID
     * @param status 审核状态
     * @param userId 审核人id
     * @return 审核结果
     */
    String review(Long fileId, Integer status, Long userId);

    /**
     * 上传文件
     *
     * @param file   温江
     * @param userId 上传用户id
     */
    void uploadFile(MultipartFile file, Long userId) throws Exception;

    /**
     * 获取文件信息
     *
     * @param fileId 文档id
     * @return 文件信息
     */
    DocumentVo getFileContent(Long fileId);
}
