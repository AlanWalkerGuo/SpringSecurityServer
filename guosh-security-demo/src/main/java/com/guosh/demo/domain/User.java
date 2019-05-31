package com.guosh.demo.domain;


import com.fasterxml.jackson.annotation.JsonView;
import com.guosh.demo.validator.MyConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel(value="用户对象")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{};

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "账号")
    //自定义注解
//    @MyConstraint(message = "账号必须是tom")
    @Column(name = "username")
    private String username;

    @ApiModelProperty(value = "密码")
    //不允许password为null
    @NotBlank(message = "密码不能为空")
    @Column(name = "password")
    private String password;

    @Column(name = "is_account_nonLocked")
    private boolean isAccountNonLocked;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "mobile")
    private String mobile;

    @ApiModelProperty(value = "生日")
    //加验证生日必须是过去的时间
    @Past(message = "生日必须是过去的时间")
    @Column(name = "birthday")
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

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
