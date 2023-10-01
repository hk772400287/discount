package com.ggw.discount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ggw.discount.common.R;
import com.ggw.discount.entity.User;
import com.ggw.discount.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public R<String> signup(@RequestBody User user) {
        log.info("Sign up...");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getName() != null, User::getName, user.getName());
        if (userService.count(queryWrapper) != 0) {
            return R.error("Username has been used");
        }
        userService.save(user);
        return R.success("Signup successful");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody User user, HttpSession session) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getName() != null, User::getName, user.getName());
        User foundUser = userService.getOne(queryWrapper);
        if (foundUser == null) {
            return R.error("Username does not existed");
        }
        if (!foundUser.getPassword().equals(user.getPassword())) {
            return R.error("Password is not correct");
        }
        session.setAttribute("user", foundUser.getId());
        return R.success(foundUser);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session) {
        session.removeAttribute("user");
        return R.success("Logout successful");
    }
}
