package com.study.zcb.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName用来设置实体类所对应的表名
//@TableName("t_user")
public class User {
    //将属性所对应的字段指定为主键
    //@Table注解的value属性用于指定主键的字段，当只编写一个属性value的时候，value可以省略不写
    //@Table注解的type属性用来设置主键生成策略
    @TableId(value = "uid",type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String userName;
    private Integer age;
    private String email;
    @TableLogic
    private Integer isDeleted;

}
