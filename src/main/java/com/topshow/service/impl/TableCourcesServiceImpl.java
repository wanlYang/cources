package com.topshow.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.topshow.constant.TopShowConstant;
import com.topshow.entity.*;
import com.topshow.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topshow.mapper.TableCourcesMapper;
import com.topshow.service.TableCourcesService;
import com.topshow.utils.UUIDUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableCourcesServiceImpl implements TableCourcesService{

    @Autowired
    private AdminService adminService;
	
	@Autowired
	private TableCourcesMapper tableCourcesMapper;

	@Override
	public List<Week> getWeek() {
		List<Week> weeks = tableCourcesMapper.getWeek();
		return weeks;
	}

    @Override
    public Result addCources(TableCources tableCources) {
        Week week = tableCourcesMapper.getWeekById(tableCources.getWeek().getId());
        if (week == null) {
            return new Result(-1, "添加失败!");
        }
        StoreFront front = tableCourcesMapper.getStoreFrontById(tableCources.getStoreFront().getId());
        if (front == null) { 
        	return new Result(-1, "添加失败!");
        }
        String star_ = "";
        for(int i = 0; i < Integer.parseInt(tableCources.getStar_class()); i++){
            star_ += TopShowConstant.STAR_ICON;
        }
        tableCources.setStar_class(star_);
        tableCources.setId(UUIDUtils.generateNumberUUID(""));
        Integer addCources = tableCourcesMapper.addCources(tableCources);
        TableCources cources = tableCourcesMapper.findCourcesById(tableCources.getId());
        return new Result(200, "添加成功!", addCources, cources);
    }

    /**
     * id  门店ID
     */
	@Override
    public List<List<TableCources>> getAllWeekCources(String id) {
    	
        List<Week> list = tableCourcesMapper.findAllWeekCources(id);
        StoreFront storeFrontById = tableCourcesMapper.getStoreFrontById(id);
        Collections.sort(list, new Comparator<Week>() {
			@Override
			public int compare(Week o1, Week o2) {
                return o1.getId().compareTo(o2.getId());
			}
		});
        List<List<TableCources>> tableCources = new ArrayList<List<TableCources>>();
        List<TableCources> cources = new ArrayList<TableCources>();
        for (int j = 0; j < 9; j++) {
        	 for (int i = 0; i < 7; i++) {
             	try {
					TableCources tableCources_ = list.get(i).getTableCources().get(j);
					cources.add(tableCources_);
				} catch (Exception e) {
					cources.add(new TableCources(null,null,null,null,null,null,null,null,storeFrontById));
				}
     		}
        	 List<TableCources> cources_ = new ArrayList<>(cources);
        	 tableCources.add(cources_);
        	 cources.clear();
		}
        return tableCources;
    }
    //
	@Override
	public List<TableCources> getAllWeekCources(String frontId, String weekId) {
		List<TableCources> findCourcesByFrontIdAndWeekId = tableCourcesMapper.findCourcesByFrontIdAndWeekId(frontId,weekId);
		
		return findCourcesByFrontIdAndWeekId;
	}

    @Override
    public Result editCources(TableCources tableCources) {
        String star_ = "";
        for(int i = 0; i < Integer.parseInt(tableCources.getStar_class()); i++){
            star_ += TopShowConstant.STAR_ICON;
        }
        tableCources.setStar_class(star_);
        Integer row = tableCourcesMapper.update(tableCources);
        if (row == 0 || row < 0) {
            return new Result(-1,"失败!");
        }

        TableCources cources = tableCourcesMapper.findCourcesById(tableCources.getId());
        return new Result(200,"编辑成功!",row,cources);
    }

    @Override
    public Result delete(String id) {
        Integer row = tableCourcesMapper.delete(id);
        if (row == 0 || row < 0) {
            return new Result(-1,"失败!");
        }
        return new Result(200,"删除成功!",row,id);
    }

	@Override
	public List<StoreFront> getAllWeekCourcesDay() {
        List<StoreFront> storeFronts = tableCourcesMapper.finAllFront();
        for (StoreFront s: storeFronts) {
            List<Week> list_ = tableCourcesMapper.findAllWeekCources(s.getId());
            if (list_ != null && list_.size() != 0){
                list_.sort(new Comparator<Week>() {
                    @Override
                    public int compare(Week o1, Week o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });
                s.setWeeks(list_);
            }
        }

        return storeFronts;

	}




    @Override
    public List<StoreFront> getFront(String admin_id) {
	    if (!StringUtils.isNotBlank(admin_id)){
            return null;
        }
	    if (admin_id.equals(TopShowConstant.SUPER_ADMIN_ID)){
            return tableCourcesMapper.finAllFront();
        }
        List<StoreFront> fronts = tableCourcesMapper.finAllFrontByAdminId(admin_id);
        return fronts;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addStore(StoreFront storeFront) {
        storeFront.setId(UUIDUtils.generateNumberUUID(""));
        Integer row = tableCourcesMapper.creatStore(storeFront);
        if (row < 0 || row == 0){
            return new Result(-1,"失败!");
        }
        Integer row_ = tableCourcesMapper.creatCourcesImagesByStore(storeFront);
        boolean init = init(storeFront.getId());


        return new Result(200,"初始化课程数据成功!",row,storeFront);
    }

    @Override
    public Result editStore(StoreFront storeFront) {
        Integer row = tableCourcesMapper.updateStore(storeFront);
        if (row < 0 || row == 0){
            return new Result(-1,"失败!");
        }

        return new Result(200,"成功!",row,storeFront);
    }



    @Override
    public List<List<List<TableCources>>> getAllWeekCourcesFront() {
        List<StoreFront> storeFronts = tableCourcesMapper.finAllFront();
        List<List<List<TableCources>>> lists = new ArrayList<>();
        for (StoreFront s: storeFronts) {
            List<Week> list = tableCourcesMapper.findAllWeekCources(s.getId());
            list.sort(new Comparator<Week>() {
                @Override
                public int compare(Week o1, Week o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            List<List<TableCources>> tableCources = new ArrayList<>();
            List<TableCources> cources = new ArrayList<TableCources>();
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 7; i++) {
                    try {
                        TableCources tableCources_ = list.get(i).getTableCources().get(j);
                        cources.add(tableCources_);
                    } catch (Exception e) {
                        cources.add(new TableCources(null,null,null,null,null,null,null,null,s));
                    }
                }
                List<TableCources> cources_ = new ArrayList<>(cources);
                tableCources.add(cources_);
                cources.clear();
            }
            List<List<TableCources>> tableCources_ = new ArrayList<>(tableCources);
            lists.add(tableCources_);
            tableCources.clear();
        }
        return lists;
    }

    /**
     * 星期数据初始化
     * @param StroeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean init(String StoreId) {
        for (int i = 0; i < 7; i++) {
            String week_id = TopShowConstant.WEEKIDS[i];
            for (int j = 0; j < 9; j++) {
                if (j < 2){
                    TableCources tableCources = new TableCources();
                    tableCources.setStart_time("12:30:00");
                    tableCources.setEnd_time("13:30:00");
                    tableCources.setStoreFront(new StoreFront(StoreId));
                    tableCources.setWeek(new Week(week_id));
                    tableCources.setStar_class(TopShowConstant.STAR_ICON);
                    tableCources.setId(UUIDUtils.generateNumberUUID(""));
                    Integer addCources = tableCourcesMapper.addCources(tableCources);
                    TableCources cources = tableCourcesMapper.findCourcesById(tableCources.getId());
                }else if(j > 1 && j < 3 ){
                    TableCources tableCources = new TableCources();
                    tableCources.setStart_time("13:45:00");
                    tableCources.setEnd_time("14:45:00");
                    tableCources.setStoreFront(new StoreFront(StoreId));
                    tableCources.setWeek(new Week(week_id));
                    tableCources.setStar_class(TopShowConstant.STAR_ICON);
                    tableCources.setId(UUIDUtils.generateNumberUUID(""));
                    Integer addCources = tableCourcesMapper.addCources(tableCources);
                    TableCources cources = tableCourcesMapper.findCourcesById(tableCources.getId());
                }else if(j >= 3 && j <= 5){
                    TableCources tableCources = new TableCources();
                    tableCources.setStart_time("18:30:00");
                    tableCources.setEnd_time("19:30:00");
                    tableCources.setStoreFront(new StoreFront(StoreId));
                    tableCources.setWeek(new Week(week_id));
                    tableCources.setStar_class(TopShowConstant.STAR_ICON);
                    tableCources.setId(UUIDUtils.generateNumberUUID(""));
                    Integer addCources = tableCourcesMapper.addCources(tableCources);
                    TableCources cources = tableCourcesMapper.findCourcesById(tableCources.getId());
                }else if (j > 5 && j < 9){
                    TableCources tableCources = new TableCources();
                    tableCources.setStart_time("19:45:00");
                    tableCources.setEnd_time("20:45:00");
                    tableCources.setStoreFront(new StoreFront(StoreId));
                    tableCources.setWeek(new Week(week_id));
                    tableCources.setStar_class(TopShowConstant.STAR_ICON);
                    tableCources.setId(UUIDUtils.generateNumberUUID(""));
                    Integer addCources = tableCourcesMapper.addCources(tableCources);
                    TableCources cources = tableCourcesMapper.findCourcesById(tableCources.getId());
                }

            }
        }
        return true;
    }
    @Override
    public List<Admin> getAllAdmin() {
        List<Admin> admins = adminService.findAllAdmin();
        return admins;
    }
    @Override
    public StoreFront  getOneFront(String admin_id) {
        StoreFront frontByAdminId = tableCourcesMapper.findFrontByAdminId(admin_id);
        return frontByAdminId;
    }

    @Override
    public StoreFront findStoreById(String store) {

        return tableCourcesMapper.getStoreFrontById(store);
    }

    @Override
    public void updateStoreForAdmin(String storeId, String adminId) {

        Integer row = tableCourcesMapper.updateStoreForAdmin(storeId,adminId);
    }

    @Override
    public Integer insertStoreForAdmin(String storeId, String adminId) {
        Integer row = tableCourcesMapper.insertStoreForAdmin(storeId,adminId);

        return row;


    }

    @Override
    public List<TableImages> getImagesList(String admin_id) {
        if (!StringUtils.isNotBlank(admin_id)){
            return null;
        }
        if (admin_id.equals(TopShowConstant.SUPER_ADMIN_ID)){
            return tableCourcesMapper.findTableImages();
        }
        List<StoreFront> fronts = tableCourcesMapper.finAllFrontByAdminId(admin_id);
        return tableCourcesMapper.findTableImagesByStoreId(fronts.get(0).getId());
    }

    @Override
    public Integer addCourcesImages(TableImages tableImages) {

        return tableCourcesMapper.creatCourcesImages(tableImages);
    }

    @Override
    public Integer deleteImages(String id) {
        return tableCourcesMapper.deleteImages(id);
    }

    @Override
    public List<TableImages> getAllWeekCourcesDayImg() {

        return tableCourcesMapper.findAllCourcesImg();
    }
}
