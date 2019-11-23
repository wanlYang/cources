package com.topshow.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.topshow.entity.Result;
import com.topshow.entity.StoreFront;
import com.topshow.entity.TableCources;
import com.topshow.entity.Week;
import com.topshow.service.TableCourcesService;

import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/admin/cources/table")

public class TableCourcesController {

	@Autowired
	private TableCourcesService tableCourcesService;

	@RequestMapping(value = "/week/list", method = RequestMethod.POST)
	@ResponseBody
	public Result getWeek() {
		Result result = new Result();
		List<Week> weeks = tableCourcesService.getWeek();
		result.setStatus(200);
		result.setMessage("获取成功@!");
		result.setData(weeks);
		return result;
	}
	
	@RequestMapping(value = "/week/list/front", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeekFront() {
        Result result = new Result();
        List<StoreFront> storeFronts = tableCourcesService.getFront();
        result.setStatus(200);
        result.setMessage("获取成功@!");
        result.setData(storeFronts);
        return result;
    }

	/**
	 * 前端接口/获取每星期第一条数据，一次类推
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/list/front/end/sideways", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCourcesFront(String id, HttpServletResponse response,
								  @RequestParam(value = "callback",required = false) final String callback) {
		Result result = new Result();
		List<List<TableCources>> lists = tableCourcesService.getAllWeekCources(id);
		result.setStatus(200);
		result.setMessage("获取成功@!");
		result.setData(lists);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "*");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Headers","Content-Type");
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JSON.toJSONString(result) + ");";
		}
		return JSON.toJSONString(result);
	}
	/**
	 * 前端接口/获取每星期一天数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/list/front/end", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCourcesFront_week(String id,HttpServletResponse response,
									   @RequestParam(value = "callback",required = false) final String callback) {
		Result result = new Result();
		List<Week> lists = tableCourcesService.getAllWeekCourcesDay(id);
		result.setStatus(200);
		result.setMessage("获取成功@!");
		result.setData(lists);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Method", "*");
		response.addHeader("Access-Control-Allow-Headers","Content-Type");
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JSON.toJSONString(result) + ");";
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 添加课程页面
	 * @return
	 */
	@RequestMapping(value = "/add/page", method = RequestMethod.GET)
	public ModelAndView add(ModelAndView modelAndView) {
		modelAndView.setViewName("/admin/cources/add_table_cources");
		return modelAndView;
	}
	
	/**
	 * 编辑
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/edit/page", method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView) {
        modelAndView.setViewName("/admin/cources/edit_table_cources");
        return modelAndView;
    }
	
	/**
	 * 课程表页面
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView courcesList(ModelAndView modelAndView) {
    	modelAndView.setViewName("/admin/cources/table_cources_list");
        return modelAndView;
    }
	
	/**
	 * 获取星期一课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/monday",method = RequestMethod.POST)
	@ResponseBody
    public Result monday(String front,String week) {
		return getCources(front, week);
    }
	
	/**
	 * 获取星期二课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/tuesday",method = RequestMethod.POST)
	@ResponseBody
    public Result tuesday(String front,String week) {
        return getCources(front, week);
    }
	
	/**
	 * 获取星期三课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/wednesday",method = RequestMethod.POST)
	@ResponseBody
    public Result wednesday(String front,String week) {
        return getCources(front, week);
    }
	/**
	 * 获取星期四课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/thursday",method = RequestMethod.POST)
	@ResponseBody
    public Result thursday(String front,String week) {
        return getCources(front, week);
    }
	/**
	 * 获取星期五课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/friday",method = RequestMethod.POST)
	@ResponseBody
    public Result friday(String front,String week) {
        return getCources(front, week);
    }
	/**
	 * 获取星期六课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/saturday",method = RequestMethod.POST)
	@ResponseBody
    public Result saturday(String front,String week) {
        return getCources(front, week);
    }
	/**
	 * 获取星期天课程表根据门店
	 * @return
	 */
	@RequestMapping(value = "/list/sunday",method = RequestMethod.POST)
	@ResponseBody
    public Result sunday(String front,String week) {
        return getCources(front, week);
    }
	
	public Result getCources(String front,String week) {
		Result result = new Result();
        List<TableCources> cources = tableCourcesService.getAllWeekCources(front,week);   
        if (cources != null) {
            result.setMessage("获取成功!");
            result.setData(cources);
            result.setStatus(200);
            return result;
        }
        result.setMessage("获取失败!");
        result.setData(null);
        result.setStatus(-1);
        return result;
	}
	
	/**
     * 添加课程页面
     * @return
     */
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result addSubmit(TableCources tableCources) {
        Result result = tableCourcesService.addCources(tableCources);
        return result;
    }
	
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result editSubmit(TableCources tableCources) {
        Result result = tableCourcesService.editCources(tableCources);
        return result;
    }
    
    @RequestMapping(value = "/delete/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String id) {
        Result result = tableCourcesService.delete(id);
        return result;
    }

	@RequestMapping(value = "/storefront/list/table", method = RequestMethod.POST)
	@ResponseBody
	public Result store() {
		List<StoreFront> result = tableCourcesService.getFront();
		return new Result(200,"获取成功!",0,result);
	}

	@RequestMapping(value = "/storefront/list", method = RequestMethod.GET)
	public ModelAndView storePage(ModelAndView modelAndView) {
		modelAndView.setViewName("/admin/cources/store_list");
		return modelAndView;
	}
	@RequestMapping(value = "/store/add", method = RequestMethod.GET)
	public ModelAndView storeAdd(ModelAndView modelAndView) {
		modelAndView.setViewName("/admin/cources/add_store");
		return modelAndView;
	}
	@RequestMapping(value = "/store/edit", method = RequestMethod.GET)
	public ModelAndView storeEdit(ModelAndView modelAndView) {
		modelAndView.setViewName("/admin/cources/edit_store");
		return modelAndView;
	}
	@RequestMapping(value = "/store/add/submit", method = RequestMethod.POST)
	@ResponseBody
	public Result addStoreSubmit(StoreFront storeFront) {
		return tableCourcesService.addStore(storeFront);
	}
	@RequestMapping(value = "/store/edit/submit", method = RequestMethod.POST)
	@ResponseBody
	public Result editStoreSubmit(StoreFront storeFront) {
		return tableCourcesService.editStore(storeFront);
	}
}
