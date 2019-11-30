package com.topshow.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.topshow.entity.*;
import com.topshow.service.TableCourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topshow.constant.TopShowConstant;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 后台页面跳转控制
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class BackStageViewController {

    @Autowired
    private TableCourcesService tableCourcesService;

    @RequestMapping(value = "/index/li",method = RequestMethod.GET)
    public ModelAndView index_li(HttpSession session, ModelAndView modelAndView) {
        List<StoreFront> lists = tableCourcesService.getAllWeekCourcesDay();
        modelAndView.setViewName("admin/index_c");
        modelAndView.addObject("list",lists);
        return modelAndView;
    }

    @RequestMapping(value = "/index/img",method = RequestMethod.GET)
    public ModelAndView index_img(HttpSession session, ModelAndView modelAndView) {
        List<TableImages> lists = tableCourcesService.getAllWeekCourcesDayImg();
        modelAndView.setViewName("admin/index_img");
        modelAndView.addObject("list",lists);
        System.out.println(lists);
        return modelAndView;
    }

    
    /**
     * 后台登陆页面跳转
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpSession session) {
    	Admin attribute = (Admin) session.getAttribute(TopShowConstant.ADMIN_SESSION);
        if (attribute == null) {
        	return "admin/login/login";
		}
        return "redirect:/admin/index";
    }
    /**
     * 后台主页面跳转
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "admin/index";
    }
    
    /**
     * 后台main页面跳转
     * @return
     */
    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main() {
        
        
        return "admin/main";
    }
    
  
}
