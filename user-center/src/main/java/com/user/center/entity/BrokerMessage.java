package com.user.center.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息发送记录表
 * </p>
 *
 * @author yym
 * @since 2022-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("broker_message")
public class BrokerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId("message_id")
    private String messageId;

    /**
     * 消息内容
     */
    @TableField("message")
    private String message;

    /**
     * 消息发送状态
     */
    @TableField("status")
    private String status;

    /**
     * 重发次数
     */
    @TableField("try_count")
    private Integer tryCount;

    /**
     * 下次重发时间
     */
    @TableField("next_try")
    private LocalDateTime nextTry;

    /**
     * 消息首次发送时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
