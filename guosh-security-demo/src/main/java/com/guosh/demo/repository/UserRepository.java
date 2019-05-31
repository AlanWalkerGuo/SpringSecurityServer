package com.guosh.demo.repository;

import com.guosh.basic.repository.BaseDao;
import com.guosh.demo.domain.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseDao<User,String> {

    /**
     * 根据账号查询
     * @param username
     * @return
     */
    public User findByUsernameOrMobile(String username, String mobile);
}
