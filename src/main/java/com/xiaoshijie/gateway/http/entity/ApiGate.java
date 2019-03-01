package com.xiaoshijie.gateway.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author duoduo
 * @since 2019-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApiGate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * type+appId+方法+参数的唯一hash值
     */
    private String hashid;

    private Integer type;

    private Integer appid;

    private Integer version;

    private String format;

    /**
     * 调用方法
     */
    private String method;

    /**
     * 调用参数
     */
    private String params;

    /**
     * api结果
     */
    private String result;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
