package com.innovation.warm.pojo.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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

    /**
     * 逻辑删除标志，0: 未删除, 1: 已删除
     */
    @TableLogic
    private Integer isDeleted;
}
