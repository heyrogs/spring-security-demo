package com.rog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
@TableName("sys_role")
public class SysRole implements GrantedAuthority {

    @TableId
    private Integer id;

    @TableField(value = "ROLE_NAME")
    private String name;

    @TableField(value = "ROLE_DESC")
    private String desc;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
