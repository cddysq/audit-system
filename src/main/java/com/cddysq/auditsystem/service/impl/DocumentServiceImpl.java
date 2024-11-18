package com.cddysq.auditsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cddysq.auditsystem.dao.Document;
import com.cddysq.auditsystem.dao.FileContent;
import com.cddysq.auditsystem.dao.User;
import com.cddysq.auditsystem.eunms.AuditStatusEnum;
import com.cddysq.auditsystem.mapper.DocumentMapper;
import com.cddysq.auditsystem.mapper.FileContentMapper;
import com.cddysq.auditsystem.mapper.UserMapper;
import com.cddysq.auditsystem.service.DocumentService;
import com.cddysq.auditsystem.vo.DocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 17:00
 * @since 1.0.0
 **/
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentMapper documentMapper;
    private final FileContentMapper fileContentMapper;
    private final UserMapper userMapper;

    @Override
    public IPage<DocumentVo> queryAllPageList(Long userId, int page, int size) {
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, Document::getUploadPersonId, userId);
        // 使用 ConcurrentHashMap 作为本地缓存
        Map<Long, User> userCache = new ConcurrentHashMap<>();

        return documentMapper.selectPage(new Page<>(page, size), wrapper).convert(document -> {
            DocumentVo documentVo = BeanUtil.copyProperties(document, DocumentVo.class);
            // 获取上传用户信息
            User uploadUser = userCache.computeIfAbsent(document.getUploadPersonId(),
                    id -> Optional.ofNullable(userMapper.selectById(id)).orElse(new User()));
            documentVo.setUploadPersonName(uploadUser.getUsername());
            // 获取审核用户信息
            if (documentVo.getReviewPersonId() != null) {
                User reviewUser = userCache.computeIfAbsent(document.getReviewPersonId(),
                        id -> Optional.ofNullable(userMapper.selectById(id)).orElse(new User()));
                documentVo.setReviewPersonName(reviewUser.getUsername());
            }

            return documentVo;
        });
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadFile(MultipartFile file, Long userId) throws Exception {
        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();

        // 保存文件信息到Document表
        Document document = new Document();
        document.setUploadPersonId(userId);
        document.setDocumentName(fileName);
        document.setSize(String.valueOf(fileSize));
        document.setStatus(AuditStatusEnum.WAIT.getCode());
        documentMapper.insert(document);

        // 保存文件内容到file_content表
        FileContent fileContent = new FileContent();
        fileContent.setDocumentId(document.getDocId());
        fileContent.setContent(file.getBytes());
        fileContentMapper.insert(fileContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String review(Long fileId, Integer status, Long userId) {
        Document document = documentMapper.selectById(fileId);
        if (document == null) {
            return "文件不存在";
        }
        if (!AuditStatusEnum.WAIT.getCode().equals(document.getStatus())) {
            return "当前文件不处于待审核状态";
        }
        document.setStatus(status);
        document.setReviewPersonId(userId);
        document.setReviewTime(new Date());
        documentMapper.updateById(document);
        return "审核成功";
    }

    @Override
    public DocumentVo getFileContent(Long fileId) {
        Document document = documentMapper.selectById(fileId);
        if (document != null) {
            DocumentVo documentVo = BeanUtil.copyProperties(document, DocumentVo.class);
            FileContent fileContent = fileContentMapper.selectOne(new LambdaQueryWrapper<FileContent>().eq(FileContent::getDocumentId, fileId));
            if (fileContent != null) {
                documentVo.setContent(fileContent.getContent());
                return documentVo;
            }
        }
        return null;
    }
}
