package com.cddysq.auditsystem.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/14 17:22
 * @since 1.0.0
 **/
@Data
@TableName("file_content")
public class FileContent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long documentId;
    private byte[] content;
}
