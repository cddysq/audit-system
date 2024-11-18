package com.cddysq.auditsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cddysq.auditsystem.eunms.RoleEnum;
import com.cddysq.auditsystem.service.DocumentService;
import com.cddysq.auditsystem.vo.DocumentVo;
import com.cddysq.auditsystem.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 17:02
 * @since 1.0.0
 **/
@Controller
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;


    @GetMapping("/files")
    public String showFilePage(Model model, HttpSession session,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "8") int size) {
        // 获取当前用户
        UserVo user = (UserVo) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        IPage<DocumentVo> filePage;
        if (RoleEnum.SUPER_ADMINISTRATOR.getDesc().equals(user.getRole())) {
            // 超管用户可以查看所有文件
            filePage = documentService.queryAllPageList(null, page, size);
        } else {
            // 普通用户只能查看自己的文件
            filePage = documentService.queryAllPageList(user.getUserId(), page, size);
        }

        model.addAttribute("uploadedFiles", filePage.getRecords());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", filePage.getPages());
        return "file-management";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpSession session, RedirectAttributes redirectAttributes) {
        UserVo user = (UserVo) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择一个文件进行上传");
            return "redirect:/files";
        }

        try {
            // 上传文件
            documentService.uploadFile(file, user.getUserId());
            redirectAttributes.addFlashAttribute("message", "文件上传成功！");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "文件上传失败！");
        }

        return "redirect:/files";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileId") Long fileId) {
        DocumentVo document = documentService.getFileContent(fileId);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", document.getDocumentName());

        // 返回文件内容
        return new ResponseEntity<>(document.getContent(), headers, HttpStatus.OK);
    }


    @PostMapping("/review")
    public String review(@RequestParam("fileId") Long fileId, @RequestParam("status") Integer status, HttpSession session, RedirectAttributes redirectAttributes) {
        UserVo user = (UserVo) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        if (!user.getRole().equals(RoleEnum.SUPER_ADMINISTRATOR.getDesc())) {
            redirectAttributes.addFlashAttribute("message", "您没有权限进行文件审核!");
            return "redirect:/files";
        }
        // 调用服务层审核文件
        String message = documentService.review(fileId, status, user.getUserId());

        // 将提示信息添加到重定向属性中
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/files";
    }

}
