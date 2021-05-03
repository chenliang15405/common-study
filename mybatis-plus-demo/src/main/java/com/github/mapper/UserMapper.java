package com.github.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author tangsong
 * @date 2021/3/13 19:27
 */
public interface UserMapper extends BaseMapper<User> {

    User findById(@Param("id") Long id);

}
