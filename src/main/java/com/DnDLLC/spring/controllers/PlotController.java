package com.DnDLLC.spring.controllers;

import com.DnDLLC.spring.models.Dot;
import com.DnDLLC.spring.models.DotRepository;
import com.DnDLLC.spring.models.User;
import com.DnDLLC.spring.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("plot/{username}")
public class PlotController {

    final DotRepository dotRepository;

    final UserRepository userRepository;

    @Autowired
    public PlotController(DotRepository dotRepository, UserRepository userRepository) {
        this.dotRepository = dotRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping
    public Collection<Dot> readDots(@PathVariable String username, HttpServletRequest request) throws UserNotFoundException {
        if(request.getSession().getAttribute("connected")==null){
            throw new UserNotFoundException(username);
        }
        return dotRepository.findByUserUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Collection<Dot> addDot(@PathVariable String username, @RequestBody Map<String,String> map) throws UserNotFoundException {

        Dot dot = new Dot();
        dot.setDateChecked(System.currentTimeMillis());
        dot.setxValue(Double.valueOf(map.get("xValue")));
        dot.setyValue(Double.valueOf(map.get("yValue").replace(',','.')));

        User user = userRepository.findDistinctByUsername(username);
        if (user == null)
            throw new UserNotFoundException(username);
        dot.setUser(user);
        dot.setWorktime(System.currentTimeMillis() - dot.getDateChecked());
        dotRepository.save(dot);
        return dotRepository.findByUserUsername(username);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class UserNotFoundException extends RuntimeException {
        UserNotFoundException(String username) {
            super("No such user " + username);
        }
    }
}
