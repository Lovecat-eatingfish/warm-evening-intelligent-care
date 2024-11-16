package com.innovation.warm.pojo.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: SuperEntity
 * PackageName: com.enquan.panel.base
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/11 下午10:19
 * @Version: 1.0
 */
@Data
public class SuperEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    // 更新字段 再添加和修改的时候都会区 更新数据
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
