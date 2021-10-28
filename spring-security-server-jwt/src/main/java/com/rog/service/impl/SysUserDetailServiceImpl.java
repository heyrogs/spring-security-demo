package com.rog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rog.entity.SysUser;
import com.rog.mapper.ISysUserMapper;
import com.rog.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/10/28
 **/
@Service
@RequiredArgsConstructor
public class SysUserDetailServiceImpl implements ISysUserService {

    private final ISysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = userMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("当前用户不存在");
        }
        return user;
    }
}
