package tw.com.vik.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import tw.com.vik.dao.UserDao;
import tw.com.vik.dto.UserRegisterRequest;
import tw.com.vik.model.User;
import tw.com.vik.rowmapper.UserRowMapper;


@Component
public class UserDaoImpl implements UserDao
{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId)
    {
        String sql = "select user_id, email, password, created_date, last_modified_date from `user` where user_id = :userId";
     
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("userId", userId);
         
         List<User> usertList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
         
         if(!usertList.isEmpty())
             return usertList.get(0);
         else
             return null;
    }
    
    @Override
    public User getUserByEmail(String email)
    {
        String sql = "select user_id, email, password, created_date, last_modified_date from `user` where email = :email";
     
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("email", email);
         
         List<User> usertList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
         
         if(!usertList.isEmpty())
             return usertList.get(0);
         else
             return null;
    }
    
    @Override
    public Integer createrUser(UserRegisterRequest userRegisterRequest)
    {
        String sql = "insert into `user`(email, password, created_date, last_modified_date) "
                + "values(:email, :password, :createdDate, :lastModifiedDate)";
  
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("email", userRegisterRequest.getEmail());
      map.put("password", userRegisterRequest.getPassword());
      
      
      Date now = new Date();
      map.put("createdDate", now);
      map.put("lastModifiedDate", now);
      
      KeyHolder keyHolder = new GeneratedKeyHolder();
      
      namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
      
      int userId = keyHolder.getKey().intValue();
      
      return userId;
    }


    
}
