package com.example.controller;


import com.example.result.Result;
import com.example.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.model.User;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-06-05
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表")
    public Result<List<User>> listUsers() {
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/add")
    public Result<String> addUser( User user) {
        userService.insertUser(user);
        return Result.success("添加成功");
    }

    @ApiOperation(value = "编辑用户")
    @PutMapping("/edit")
    public  Result<String> editUserForm(@RequestBody User user) {
       userService.updateUser(user);
        return Result.success("编辑成功");
    }

    @ApiOperation(value = "删除用户")
    @GetMapping("/delete/{id}")
    public  Result<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }
}
