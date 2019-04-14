package com.guosh.security.browser.repository;

import com.guosh.security.base.repository.BaseDao;
import com.guosh.security.browser.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseDao<User,String> {

    /**
     * 根据账号查询
     * @param username
     * @return
     */
    public User findByUsername(String username);
}
