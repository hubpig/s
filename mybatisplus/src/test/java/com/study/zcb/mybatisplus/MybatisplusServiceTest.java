package com.study.zcb.mybatisplus;

import com.study.zcb.mybatisplus.entity.User;
import com.study.zcb.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MybatisplusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount(){
        long count = userService.count();
        System.out.println(count);
    }

    @Test
    public void testInsertMore(){
        List<User> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            User user = new User();
            //user.setId(100L);
            user.setUserName("zcb"+i);
            user.setAge(20+i);
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        System.out.println(b);
    }
}
