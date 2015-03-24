package com.fullerton.controller;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.fullerton.model.User;

@Controller
public class AuthenticationController
{
    private static final Logger log = LogManager.getLogger();
    private static final Map<String, String> userDatabase = new Hashtable<>();


    @RequestMapping("logout")
    public View logout(HttpSession session)
    {
        if(log.isDebugEnabled())
            log.debug("User {} logged out.", session.getAttribute("username"));
        session.invalidate();

        return new RedirectView("/login", true, false);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session)
    {
        if(session.getAttribute("username") != null)
            return this.getMainRedirect();

        model.put("loginFailed", false);
        model.put("loginForm", new User());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
                              HttpServletRequest request, User user)
    {
        if(session.getAttribute("username") != null)
            return this.getMainRedirect();

        if(user.getUsername() == null || user.getPassword() == null ||
                !userDatabase.containsKey(user.getUsername()) ||
                !user.getPassword().equals(userDatabase.get(user.getUsername())))
        {
            log.warn("Login failed for user {}.", user.getUsername());
            user.setPassword(null);
            model.put("loginFailed", true);
            model.put("loginForm", user);
            return new ModelAndView("login");
        }

        log.debug("User {} successfully logged in.", user.getUsername());
        session.setAttribute("username", user.getUsername());
        request.changeSessionId();
        return this.getMainRedirect();
    }
    @RequestMapping(value="createUser", method = RequestMethod.GET)
    public ModelAndView createUser(Map<String, Object> model, HttpSession session){
    	if(session.getAttribute("username") != null)
            return this.getMainRedirect();
    	model.put("createFailed", false);
        model.put("userForm", new User());

        return new ModelAndView("createUser");
    };
    @RequestMapping(value="createUser", method = RequestMethod.POST)
    public ModelAndView createUser(Map<String, Object> model, HttpSession session,
    		HttpServletRequest request, User user){
    	if(session.getAttribute("username") != null)
            return this.getMainRedirect();
    	System.out.println("User: "+user.getUsername() + user.getPassword());
        if(user.getUsername() == null || user.getPassword() == null ||
                userDatabase.containsKey(user.getUsername()))
        {
            log.warn("Created failed for user {}.", user.getUsername());
            user.setPassword(null);
            model.put("createFailed", true);
            model.put("userForm", user);
            model.put("userResult", false);
            return new ModelAndView("createUser");
        }else{
        	this.userDatabase.put(user.getUsername(), user.getPassword());
        	model.put("userResult", true);
        	System.out.println("User created");
        	return this.getLogin();
        }
    };
    private ModelAndView getMainRedirect()
    {
        return new ModelAndView(new RedirectView("/main/list", true, false));
    }
    private ModelAndView getLogin(){
    	return new ModelAndView(new RedirectView("/login",true,false));
    };

}
