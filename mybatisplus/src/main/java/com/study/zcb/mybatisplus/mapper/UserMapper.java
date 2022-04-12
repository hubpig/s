package com.study.zcb.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.zcb.mybatisplus.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User>{
    /**
     * 根据id查询用户信息返回map集合
     * @param id
     * @return
     */
    Map<String,Object> selectMapById(Long id);
}
