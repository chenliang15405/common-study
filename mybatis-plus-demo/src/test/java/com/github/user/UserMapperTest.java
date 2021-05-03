package com.github.user;

import com.github.entity.User;
import com.github.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author tangsong
 * @date 2021/3/13 19:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void userTest() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void findByIdTest() {
        User user = userMapper.findById(1L);
        System.out.println(user);
    }

}
