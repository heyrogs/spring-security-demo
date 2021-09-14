package com.rog.serivce.impl;

import com.rog.serivce.IUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@Service
public class UserServiceImpl implements IUserService {

    private static final Map<String, User> USER_MAP = new HashMap<>();

    static {
        USER_MAP.put("admin", new User("admin", "$2a$16$Sv.sti3gJzP6duQolxS0Ku0h6PZc.xx1D0viYYr4aqrIKlvlLxnUi", Arrays.asList(new SimpleGrantedAuthority("admin"))));
        USER_MAP.put("test", new User("test", "$2a$16$Sv.sti3gJzP6duQolxS0Ku0h6PZc.xx1D0viYYr4aqrIKlvlLxnUi", Arrays.asList(new SimpleGrantedAuthority("test"))));
        USER_MAP.put("test02", new User("test02", "$2a$16$Sv.sti3gJzP6duQolxS0Ku0h6PZc.xx1D0viYYr4aqrIKlvlLxnUi", Arrays.asList(new SimpleGrantedAuthority("a"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = USER_MAP.get(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名错误");
        }
        return user;
    }
}
