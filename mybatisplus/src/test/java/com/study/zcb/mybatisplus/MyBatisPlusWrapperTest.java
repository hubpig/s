package com.study.zcb.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.study.zcb.mybatisplus.entity.User;
import com.study.zcb.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.lang.model.type.IntersectionType;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        //场景：查询用户名包含a，年龄在20-30之间，邮箱信息不为null的用户信息
        QueryWrapper<User> queryMrapper = new QueryWrapper<>();
        queryMrapper.like("name","a")
                .between("age",20,30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryMrapper);
        for (User user:users){
            System.out.println(user);
        }
    }

    @Test
    public void test02(){
        //查询用户信息，按照年龄的降序排序，若年龄相同，则按照id升序排序
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age")
                .orderByAsc("uid");
        List<User> users = userMapper.selectList(queryWrapper);
        for(User user:users){
            System.out.println(user);
        }
    }

    @Test
    public void test03(){
        //删除邮箱为null的数据
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.isNull("email");
        int delete = userMapper.delete(updateWrapper);
        System.out.println(delete);
    }

    @Test
    public void test04(){
        //将年龄大于20并且用户名中包含有a或者邮箱为null的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.gt("age",20)
                .like("name","a")
                .or()
                .isNull("email");
        User user = new User();
        user.setUserName("小明");
        user.setEmail("1740136268@qq.com");
        //第一个参数用来设置修改的内容
        //第二个参数用来设置被修改的条件
        int result = userMapper.update(user, updateWrapper);
        System.out.println(result);
    }

    @Test
    public void test05(){
        //测试条件的优先级
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        //lambda中的条件优先执行
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","a")
                .and(i->i.gt("age",20).or().isNull("email"));
        User user = new User();
        user.setUserName("小红");
        int result = userMapper.update(user, queryWrapper);
        System.out.println(result);
    }

    @Test
    public void test06(){
        //查询用户的用户名，年龄，邮箱信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uid","name");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        for (Map<String,Object> ma:maps){
            System.out.println(ma);
        }
    }

    @Test
    public void test07(){
        //查询id小于等于100的用户信息 使用子查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("uid","select uid from t_user where uid <=100");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user :users){
            System.out.println(user);
        }
    }

    @Test
    public void test08(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("name","a")
                .and(i->i.gt("age",20).or().isNull("email"));
        updateWrapper.set("name","小黑");
        int update = userMapper.update(null, updateWrapper);
        System.out.println(update);
    }

    @Test
    public void test09(){
        //判断是否将条件组装进SQL语句
        String username="";
        Integer ageBegin=20;
        Integer ageEnd=30;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //isNotBlank用来判断某个字符串是否不为空字符串，不为null，不为空字符串
        if (StringUtils.isNotBlank(username)){
            userQueryWrapper.like("name",username);
        }
        if (ageBegin != null){
            userQueryWrapper.ge("age",ageBegin);
        }
        if (ageEnd != null){
            userQueryWrapper.le("age",ageEnd);
        }
        List<User> users = userMapper.selectList(userQueryWrapper);
        for (User user:users){
            System.out.println(user);
        }
    }

    @Test
    public void test10(){
        String username="a";
        Integer ageBegin=null;
        Integer ageEnd=30;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(username),"name",username)
                .ge(ageBegin !=null,"age",ageBegin)
                .le(ageEnd != null,"age",ageEnd);
        List<User> users = userMapper.selectList(userQueryWrapper);
        for (User user :users){
            System.out.println(user);
        }
    }

    @Test
    public void test11(){
        String username="a";
        Integer ageBegin=null;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(username),User::getUserName,username)
                .ge(ageBegin!=null,User::getAge,ageBegin)
                .le(ageEnd!=null,User::getAge,ageEnd);
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        for (User  user:users){
            System.out.println(user);
        }
    }
    @Test
    public void test12(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.like(User::getUserName,"a")
                .and(i->i.ge(User::getAge,20).or().isNull(User::getEmail));
        userLambdaUpdateWrapper.set(User::getUserName,"小兰").set(User::getEmail,"xiaolan@qq.com");
        int update = userMapper.update(null, userLambdaUpdateWrapper);
        System.out.println(update);
    }
}
