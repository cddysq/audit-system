package com.cddysq.auditsystem.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cddysq.auditsystem.eunms.AuditStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 16:57
 * @since 1.0.0
 **/
@Data
@TableName("document")
public class Document {
    /**
     * 文档id
     */
    @TableId(type = IdType.AUTO)
    private Long docId;
    /**
     * 上传用户id
     */
    private Long uploadPersonId;
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
     * 审核时间
     */
    private Date reviewTime;
    /**
     * 审核状态
     *
     * @see AuditStatusEnum#getCode()
     */
    private Integer status;
}
