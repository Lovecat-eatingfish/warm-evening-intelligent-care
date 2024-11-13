package com.innovation.warm.pojo.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * ClassName: BaseEntity
 * PackageName: com.enquan.panel.base
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/11 下午10:15
 * @Version: 1.0
 */
@Data
public class BaseEntity extends SuperEntity {

    @TableField(value = "creator_id")
    private Long creatorId;


    @TableField(value = "updater_id")
    private Long updaterId;
}
