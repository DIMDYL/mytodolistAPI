package org.example.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.api.pojo.entity.User;

@Mapper
public interface UserMapper {
    public User queryUser(User user);

    void insertOne(User user);

    void updateUserInfo(User user);
}
