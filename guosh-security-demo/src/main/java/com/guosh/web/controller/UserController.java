package com.guosh.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.guosh.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(method = RequestMethod.POST)
    @JsonView(User.UserSimpleView.class)
    //@Valid启用校验password不允许为空
    public User createUser(@Valid @RequestBody User user, BindingResult errors){
        //如果校验有错误是true并打印错误信息
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }

    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.PUT)
    @JsonView(User.UserSimpleView.class)
    //@Valid启用校验password不允许为空
    public User updateUser(@Valid @RequestBody User user, BindingResult errors){
        //如果校验有错误是true并打印错误信息
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId("1");
        return user;
    }


    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.DELETE)
    //@Valid启用校验password不允许为空
    public void deleteUser(@PathVariable String id){
        System.out.println(id);
    }


    @RequestMapping(method = RequestMethod.GET)
    //只显示用户名
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@RequestParam(name = "username",required = true) String username, @PageableDefault(page = 1,size = 20,sort = "username",direction = Sort.Direction.DESC) Pageable pageable){
        List<User>users=new ArrayList();
        users.add(new User("1","aaa","111",null));
        users.add(new User("2","bbb","222",null));
        users.add(new User("3","ddd","333",null));
        return users;
    }

    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    // 正则表达式 :\\d+ 表示只能输入数字
    //用户名密码都显示
    @JsonView(User.UserDetailView.class)
    public User  userInfo(@PathVariable String id){
        User user=new User();
        user.setUsername("tom");
        return user;
    }
}
