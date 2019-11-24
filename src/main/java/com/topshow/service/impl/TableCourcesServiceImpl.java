package com.topshow.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.topshow.constant.TopShowConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topshow.entity.Result;
import com.topshow.entity.StoreFront;
import com.topshow.entity.TableCources;
import com.topshow.entity.Week;
import com.topshow.mapper.TableCourcesMapper;
import com.topshow.service.TableCourcesService;
import com.topshow.utils.UUIDUtils;

@Service
public class TableCourcesServiceImpl implements TableCourcesService{
	
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
        Collections.sort(list, new Comparator<Week>() {
			@Override
			public int compare(Week o1, Week o2) {
                return o1.getId().compareTo(o2.getId());
			}
		});
        List<List<TableCources>> tableCources = new ArrayList<List<TableCources>>();
        List<TableCources> cources = new ArrayList<TableCources>();
        for (int j = 0; j <= 10; j++) {
        	 for (int i = 0; i < list.size(); i++) {
             	try {
					TableCources tableCources_ = list.get(i).getTableCources().get(j);
					cources.add(tableCources_);
				} catch (Exception e) {
					cources.add(new TableCources());
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
	public List<Week> getAllWeekCourcesDay(String id) {
		List<Week> list = tableCourcesMapper.findAllWeekCources(id);
        Collections.sort(list, new Comparator<Week>() {
			@Override
			public int compare(Week o1, Week o2) {
                return o1.getId().compareTo(o2.getId());
			}
		});
        return list;
	}

    @Override
    public List<StoreFront> getFront() {
        List<StoreFront> fronts = tableCourcesMapper.finAllFront();
        return fronts;
    }

    @Override
    public Result addStore(StoreFront storeFront) {
        storeFront.setId(UUIDUtils.generateNumberUUID(""));
        Integer row = tableCourcesMapper.creatStore(storeFront);
        if (row < 0 || row == 0){
            return new Result(-1,"失败!");
        }

        return new Result(200,"成功!",row,storeFront);
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
            for (int j = 0; j <= 10; j++) {
                for (Week week : list) {
                    try {
                        TableCources tableCources_ = week.getTableCources().get(j);
                        cources.add(tableCources_);
                    } catch (Exception e) {
                        cources.add(new TableCources());
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
}
