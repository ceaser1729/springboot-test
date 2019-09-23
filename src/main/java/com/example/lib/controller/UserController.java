package com.example.lib.controller;

import com.example.lib.entity.User;
import com.example.lib.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "user", description = "用户控制器Api")
@RestController
@RequestMapping(value = {"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "测试方法")
    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public String hello(){
        return "Hello world!";
    }

    @ApiOperation(value = "注册/添加用户")
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String add(@ApiParam(name = "user", value = "用户实体", required = true) @RequestBody User user){
        return String.valueOf(userService.addUser(user));
    }

    @ApiOperation(value = "编辑用户")
    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    public String edit(@ApiParam(name = "user", value = "用户实体", required = true) @RequestBody User user){
        return String.valueOf(userService.editUser(user));
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public void login(@RequestParam("username")String username, @RequestParam("password")String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> list = userService.selectByUsernameAndPassword(username, password);
        if(list!=null && list.size()>0){
            request.getSession().setAttribute("USERINFO", list.get(0));
            response.sendRedirect("/success.html");
        }else{
            response.sendRedirect("/failure.html");
        }
    }

    @ApiOperation(value = "通过主键查询用户")
    @RequestMapping(value = {"/get/{id}"}, method = RequestMethod.GET)
    public User get(@PathVariable("id")Long id){
        User user = userService.selectById(id);
        return user;
    }

    @ApiOperation(value = "查询所有用户")
    @RequestMapping(value = {"/getAll"}, method = RequestMethod.POST)
    public List<User> getAll(@RequestBody User user){
        return userService.selectAll(user);
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public String delete(@PathVariable("id")Long id){
        return String.valueOf(userService.deleteById(id));
    }

    @ApiOperation(value = "分页查询用户")
    @RequestMapping(value = {"/getTable/{page}/{size}"}, method = RequestMethod.POST)
    public PageInfo<User> getTable(@ApiParam(name = "page", value = "前往页码", required = true) @PathVariable("page") Integer page,
                                   @ApiParam(name = "size", value = "数据尺寸", required = true) @PathVariable("size") Integer size,
                                   @ApiParam(name = "user", value = "用户实体", required = true) @RequestBody User user){
        return userService.getTable(page, size, user);
    }

    @ApiOperation(value = "用户注销")
    @RequestMapping(value = {"/loginout"}, method = RequestMethod.POST)
    public String loginout(HttpServletRequest request) {
        request.getSession().removeAttribute("USERINFO");
        return "1";
    }

    @ApiOperation(value = "初始化用户信息")
    @RequestMapping(value = {"/initLogin"}, method = RequestMethod.POST)
    public User initLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("USERINFO");
        return user;
    }
}
