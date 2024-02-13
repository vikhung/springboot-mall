package tw.com.vik.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import tw.com.vik.dao.UserDao;
import tw.com.vik.dto.UserLoginRequest;
import tw.com.vik.dto.UserRegisterRequest;
import tw.com.vik.model.User;
import tw.com.vik.service.UserService;

@Component
public class UserServiceImpl implements UserService
{
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest)
    {
        //檢查Email是否存在
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user != null)
        {
            log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
        //生成MD5 HashValue
        String hashedPass = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPass);
        
        
        //註冊使用者帳號
        return userDao.createrUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest)
    {
        //檢查User是否存在
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if(user == null)
        {
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
        
        
        //生成MD5 HashValue
        String hashedPass = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        
        
        //檢查密碼是否正確
        if(user.getPassword().equals(hashedPass))
        {
            return user;
        }
        else
        {
            log.warn("該 email {} 密碼錯誤", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    
    @Override
    public User getUserById(Integer userId)
    {
        return userDao.getUserById(userId);
    }
}
