package com.rog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
@Data
@TableName("sys_user")
public class SysUser implements UserDetails {
    private static final long serialVersionUID = 4380924249211000675L;

    @TableId
    private Integer id;

    private String username;

    private String password;

    @TableField(exist = false)
    private Collection<SysRole> authorities;

    @TableField(exist = false)
    private boolean enabled = true;

    @TableField(exist = false)
    private boolean isAccountNonExpired = true;

    @TableField(exist = false)
    private boolean isAccountNonLocked = true;

    @TableField(exist = false)
    private boolean isCredentialsNonExpired = true;

}
