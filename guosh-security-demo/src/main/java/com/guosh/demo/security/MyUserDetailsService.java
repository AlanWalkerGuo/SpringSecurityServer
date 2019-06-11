package com.guosh.demo.security;

import com.guosh.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService , SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;

    //表单登陆
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名:" + username);
        return buildUser(username);
    }


    /**
     * 第三方登录
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String username) throws UsernameNotFoundException {
        logger.info("第三方登录用户Id:" + username);
        return buildUser(username);
    }


    private SocialUserDetails buildUser(String username) {
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

        return new SocialUser(username,userRepository.findByUsernameOrMobile(username,username).getPassword(),
                true,true,true,userRepository.findByUsernameOrMobile(username,username).isAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
