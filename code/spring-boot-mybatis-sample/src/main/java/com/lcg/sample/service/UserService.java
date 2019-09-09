package com.lcg.sample.service;

import com.lcg.sample.entity.User;
import com.lcg.sample.mapper.OtherUserMapper;
import com.lcg.sample.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final OtherUserMapper one;
    private final UserMapper two;

    public void saveByOne(User user){
        one.insert(user);
    }

    public void saveByTwo(User user){
        two.insert(user);
    }

    public List<User> queryAllByOne(){
        return one.getAll();
    }
    public List<User> queryAllByTwo(){
        return two.getAll();
    }
}
