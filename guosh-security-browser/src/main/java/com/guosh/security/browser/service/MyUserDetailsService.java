package com.guosh.security.browser.service;

import com.guosh.security.browser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //认证登陆
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //isAccountNonExpired 账号是否过期
        //isCredentialsNonExpired 密码是否过期
        //isAccountNonLocked 账号是否锁定用于冻结状态
        //isEnabled 账号是否可用用于删除状态

        //第一个参数是传进来的账号，第二个参数是数据库根据账号查询到到密码
        //第三个参数是账号是否可用，第四个参数是账号是否过期
        //第五个参数是密码是否过期，第六个参数账号是否锁定
        //第七个参数账号到权限
        com.guosh.security.browser.domain.User user=userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }

        return new User(username,userRepository.findByUsername(username).getPassword(),
                true,true,true,userRepository.findByUsername(username).getIsAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
    //模拟注册
    public void register(){
        com.guosh.security.browser.domain.User user=new com.guosh.security.browser.domain.User();
        user.setUsername("ceshi");
        //存入加密后的密码
        user.setPassword(passwordEncoder.encode("123456"));
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(true);
        userRepository.save(user);
    }

}
