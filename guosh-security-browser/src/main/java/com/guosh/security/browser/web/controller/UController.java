package com.guosh.security.browser.web.controller;

import com.guosh.security.browser.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/UController")
public class UController {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @RequestMapping(method = RequestMethod.GET)
    public void register(){
       myUserDetailsService.register();
    }

}
