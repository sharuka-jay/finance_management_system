package com.example.user_service.repo;

import com.example.user_service.model.User;
import com.example.user_service.model.dto.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl implements UserRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int registerUser(User user) {
        int result;
        String sql = "INSERT INTO `user`( `password`, `user_email`, `user_name`) VALUES (?,?,?)";
        try {
            result = jdbcTemplate.update(sql, user.getPassword(), user.getUser_email(), user.getUser_name());
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public User findByName(String name) {
        String sql = "SELECT * FROM `user` WHERE `user_name`= ? ";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{name}, new UserRowMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null; // User not found
        }

    }

    @Override
    public User findByNameAndPassword(String name, String encordedPassword) {
        String sql = "SELECT * FROM `user` WHERE `user_name` = ? and `password`= ?;";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{name, encordedPassword}, (rs, rowNum) -> {
                User u = new User();
                // Map the columns from the result set to the User object fields
                u.setUser_id(rs.getInt("user_id"));
                u.setUser_name(rs.getString("user_name"));
                u.setPassword(rs.getString("password"));
                return u;
            });
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null; // User not found
        }
    }


}
