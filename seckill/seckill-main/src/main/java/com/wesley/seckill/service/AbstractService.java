package com.wesley.seckill.service;

import com.wesley.seckill.util.MyMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T>{

    @Autowired
    protected MyMapper<T> myMapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        myMapper.insertSelective(model);
    }

    public void save(List<T> models) {
        myMapper.insertList(models);
    }

    public void deleteById(Integer id) {
        myMapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        myMapper.deleteByIds(ids);
    }

    public void update(T model) {
        myMapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Integer id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @SuppressWarnings("unchecked")
	public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return myMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("查询操作异常!");
        }
    }

    public List<T> findByIds(String ids) {
        return myMapper.selectByIds(ids);
    }

    public List<T> findByCondition(Example example) {
        return myMapper.selectByExample(example);
    }

    public List<T> findAll() {
        return myMapper.selectAll();
    }
}
