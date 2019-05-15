package com.guosh.demo.domain;


import com.fasterxml.jackson.annotation.JsonView;
import com.guosh.demo.validator.MyConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel(value="用户对象")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{};
    private String id;

    @ApiModelProperty(value = "账号")
    //自定义注解
    @MyConstraint(message = "账号必须是tom")
    private String username;

    @ApiModelProperty(value = "密码")
    //不允许password为null
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "生日")
    //加验证生日必须是过去的时间
    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() { return birthday; }

    public void setBirthday(Date birthday) { this.birthday = birthday; }
}