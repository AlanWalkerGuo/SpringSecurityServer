package com.guosh.demo.security;

import com.guosh.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService , SocialUserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    //表单登陆
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
        com.guosh.demo.domain.User user=userRepository.findByUsernameOrMobile(username,username);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }

        return new User(username,userRepository.findByUsernameOrMobile(username,username).getPassword(),
                true,true,true,userRepository.findByUsernameOrMobile(username,username).isAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


    /**
     * 第三方登录
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        com.guosh.demo.domain.User user=userRepository.findOne(userId);
        if(user==null){
            throw new UsernameNotFoundException(userId);
        }

        return new SocialUser(userId,userRepository.findOne(userId).getPassword(),
                true,true,true,userRepository.findOne(userId).isAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
