package tw.com.vik.dao;

import tw.com.vik.dto.UserRegisterRequest;
import tw.com.vik.model.User;

public interface UserDao
{

    Integer createrUser(UserRegisterRequest userRegisterRequest);
    
    User getUserById(Integer userId);
    
    User getUserByEmail(String email);
}
