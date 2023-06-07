package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public int registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int res = userRepo.registerUser(user);
        return res;

    }

    public User loginUser(User user) {
        String name = user.getUser_name();
        // find name
        User user1 = userRepo.findByName(name);
        if (user1 != null) {
            String password = user.getPassword();
            String encordedPassword = user1.getPassword();
            //check the password is right
            Boolean isPasswordRight = passwordEncoder.matches(password, encordedPassword);
            if (isPasswordRight) {
                User user2 = userRepo.findByNameAndPassword(name,encordedPassword);
                if (user2 != null) {
                    return user2;
                } else {
                    //null enne
                    return null;
                }
            }else {
                return null;
            }
        }else {
            //null enne
            return null;
        }
    }



//    public String loginUser(User user) {
//        String name = user.getUser_name();
//
//        // find name
//        User user1 = userRepo.findByName(name);
//        if (user1 != null) {
//            String password = user.getPassword();
//            String encordedPassword = user1.getPassword();
//            //check the password is right
//            Boolean isPasswordRight = passwordEncoder.matches(password, encordedPassword);
//            if (isPasswordRight) {
//                User user2 = userRepo.findByNameAndPassword(name,encordedPassword);
//                if (user2 != null) {
//                    return ("Login Success");
//                } else {
//                    return ("Login failed");
//                }
//            }else {
//                return ("password not match");
//            }
//        }else {
//            return ("user Name not exit");
//        }
//    }
}
