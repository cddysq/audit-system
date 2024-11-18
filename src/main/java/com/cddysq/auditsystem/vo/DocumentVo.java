package com.cddysq.auditsystem.vo;

import com.cddysq.auditsystem.eunms.AuditStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:57
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVo {
    /**
     * 文档id
     */
    private Long docId;
    /**
     * 上传用户id
     */
    private Long uploadPersonId;
    /**
     * 上传用户账号名
     */
    private String uploadPersonName;
    /**
     * 文档名称
     */
    private String documentName;
    /**
     * 文档大小
     */
    private String size;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 审核人id
     */
    private Long reviewPersonId;
    /**
     * 审核人账号名
     */
    private String reviewPersonName;
    /**
     * 审核时间
     */
    private Date reviewTime;
    /**
     * 审核状态
     *
     * @see AuditStatusEnum#getCode()
     */
    private String status;
    /**
     * 文件流
     */
    private byte[] content;
}
