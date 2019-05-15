package com.guosh.basic.service.impl;


import com.guosh.basic.repository.BaseDao;
import com.guosh.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * 通用CRUD服务
 *2015/3/28.
 */
public abstract class BaseServiceImpl<T, ID extends Serializable>  implements BaseService<T, ID> {

    @Autowired
    protected BaseDao<T, ID> baseDao;
    /**
     * 保存
     *
     * @param entity
     */
    @Override
    public <S extends T> S save(S entity){
        return baseDao.save(entity);
    }

    /**
     * 批量保存
     *
     * @param entities
     * @return
     */
    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return baseDao.save(entities);
    }

    /**
     * 通过ID删除
     *
     * @param id
     */
    @Override
    public void delete(ID id) {
        baseDao.delete(id);
    }

    /**
     * 删除实体
     *
     * @param entity
     */
    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    /**
     * 通过ID批量删除
     *
     * @param ids
     */
    @Override
    public void delete(Iterable<ID> ids) {
        for (ID id : ids) {
            delete(id);
        }
    }
    

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    @Override
    public T findById(ID id) {
        return baseDao.findOne(id);
    }
    
    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }



    
}
