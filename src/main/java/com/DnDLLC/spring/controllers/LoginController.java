package com.DnDLLC.spring.controllers;

import com.DnDLLC.spring.models.CheckSumCalculator;
import com.DnDLLC.spring.models.User;
import com.DnDLLC.spring.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import sun.security.util.Password;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping(value="/authentication")
@RestController
public class LoginController{

    final
    UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping
    public boolean simpleGet(HttpServletRequest request){
        if(request.getSession().getAttribute("connected")!=null){
            return true;
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean logUserIn(@RequestBody Map<String,String> map, HttpServletRequest request, HttpServletResponse response){
        if(request.getSession().getAttribute("connected")!=null){
            return true;
        }
        String username=map.get("username");
        String password=map.get("password");
        System.out.println(map.get("username") + " " + map.get("password"));
        if(password.length()>20||password.length()<5)
            return false;
        if(userRepository.findByUsername(username)==null)
            return false;
        if(!new CheckSumCalculator().calculate(password).equals(userRepository.findDistinctByUsername(username).getPassword()))
            return false;
        request.getSession().setAttribute("connected",username);
        response.addCookie(new Cookie("username",username));
        return true;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public boolean addNewUser(@RequestBody Map<String,String> map, HttpServletResponse response){
        String username=map.get("username");
        String password=map.get("password");
        if(password.length()>20||password.length()<5)
            return false;
        if(userRepository.findByUsername(username)!=null)
            return false;
        userRepository.save(new User(username, password));
        return true;
    }


    //logout        _________
    //              |       |
    //              |       |
    //              |       |
    //          ____|       |____
    //          \               /
    //           \             /
    //            \           /
    //             \         /
    //              \       /
    //               \     /
    //                \   /
    //                 \ /

//    @RequestMapping(method = RequestMethod.DELETE)
//    public boolean logout(HttpServletRequest request){
//        request.getSession().
//    }

}
