package com.study.zcb.mybatisplus;

import com.study.zcb.mybatisplus.entity.User;
import com.study.zcb.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisplusApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		//通过条件构造器查询得到一个list集合，若没有条件，则可以设置null为参数
		List<User> users = userMapper.selectList(null);
		for (User user:users){
			System.out.println(user);
		}
	}

	@Test
	public void testInsert(){
		//实现新增用户信息
		//INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
		User user = new User();
		user.setUserName("张三");
		user.setAge(32);
		user.setEmail("zhangsan@qq.com");
		int result = userMapper.insert(user);
		System.out.println("result："+result);
		System.out.println("id："+user.getId());
	}

	@Test
	public void testDelete(){
		//通过id删除用户信息
		/*int result = userMapper.deleteById(1511638450237874178L);
		System.out.println("result："+result);*/

		//根据map集合中所设置kv键值对的条件删除用户信息
		/*Map<String,Object> map=new HashMap<>();
		map.put("name","张三");
		map.put("age","32");
		int result = userMapper.deleteByMap(map);
		System.out.println("result："+result);*/

		//通过多个id进行批量删除
		//DELETE FROM user WHERE id IN ( ? , ? , ? )
		// Arrays.asList(),把当中的一些数据直接转换成一个集合
		List<Long> list = Arrays.asList(1L, 2L, 3L);
		int result = userMapper.deleteBatchIds(list);
		System.out.println("result："+result);
	}

	@Test
	public void testUpdate(){
		User user = new User();
		user.setId(4L);
		user.setUserName("李四");
		user.setEmail("lisi@qq.com");
		int result = userMapper.updateById(user);
		System.out.println("result："+result);
	}

	@Test
	public void testSelect(){
		//通过id查询用户信息
		//SELECT id,name,age,email FROM user WHERE id=?
		User user = userMapper.selectById(1L);
		System.out.println(user);

		//根据多个id查询多个用户信息
		//SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
		List<Long> list = Arrays.asList(1L, 2L, 3L);
		List<User> users = userMapper.selectBatchIds(list);
		for (User user1:users){
			System.out.println(user1);
		}

		//根据map集合中所设置kv键值对的条件查询用户信息
		//SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
		Map<String,Object> map=new HashMap<>();
		map.put("name","Jack");
		map.put("age",20);
		List<User> users1 = userMapper.selectByMap(map);
		for (User user1:users1){
			System.out.println(user1);
		}

		//查询所有数据
		//SELECT id,name,age,email FROM user
		List<User> users2 = userMapper.selectList(null);
		for (User user1:users2){
			System.out.println(user1);
		}

		//测试自定义方法
		Map<String, Object> map1 = userMapper.selectMapById(1L);
		System.out.println(map1);
	}
}
