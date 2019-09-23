package com.example.lib.service;
import com.example.lib.entity.User;
import com.example.lib.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int addUser(User user){
        return userMapper.insertSelective(user);
    }

    public int editUser(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<User> selectByUsernameAndPassword(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userMapper.selectByUser(user);
    }

    public List<User> selectAll(User user){
        return userMapper.selectByUser(user);
    }

    public User selectById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int deleteById(Long id){
        return userMapper.deleteByPrimaryKey(id);
    }

    public PageInfo<User> getTable(Integer page, Integer size, User user){
        PageHelper.startPage(page, size);
        List<User> list = userMapper.selectByUser(user);
        PageInfo<User> pageInfo = new PageInfo<>(list,size);
        return pageInfo;
    }
}
