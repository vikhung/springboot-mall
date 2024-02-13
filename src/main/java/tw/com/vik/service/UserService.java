package tw.com.vik.service;

import tw.com.vik.dto.UserLoginRequest;
import tw.com.vik.dto.UserRegisterRequest;
import tw.com.vik.model.User;

public interface UserService
{

    Integer register(UserRegisterRequest userRegisterRequest);
    
    User login(UserLoginRequest userLoginRequest);
    
    User getUserById(Integer userId);
}
