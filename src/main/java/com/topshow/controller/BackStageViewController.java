package com.topshow.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.topshow.entity.Result;
import com.topshow.entity.StoreFront;
import com.topshow.entity.TableCources;
import com.topshow.service.TableCourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topshow.constant.TopShowConstant;
import com.topshow.entity.Admin;
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

    @RequestMapping(value = "/index/g",method = RequestMethod.GET)
    public ModelAndView index_g(HttpSession session, ModelAndView modelAndView) {
        Result result = new Result();
        List<List<List<TableCources>>> lists = tableCourcesService.getAllWeekCourcesFront();
        result.setStatus(200);
        result.setMessage("获取成功@!");
        result.setData(lists);
        modelAndView.setViewName("/admin/index_g");
        modelAndView.addObject("list",lists);
        return modelAndView;
    }

    @RequestMapping(value = "/index/li",method = RequestMethod.GET)
    public ModelAndView index_li(HttpSession session, ModelAndView modelAndView) {
        List<StoreFront> lists = tableCourcesService.getAllWeekCourcesDay();
        modelAndView.setViewName("/admin/index_c");
        modelAndView.addObject("list",lists);
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
