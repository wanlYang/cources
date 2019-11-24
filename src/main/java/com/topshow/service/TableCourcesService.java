package com.topshow.service;

import java.util.List;

import com.topshow.entity.Result;
import com.topshow.entity.StoreFront;
import com.topshow.entity.TableCources;
import com.topshow.entity.Week;

public interface TableCourcesService {

	List<Week> getWeek();

	Result addCources(TableCources tableCources);

    List<List<TableCources>> getAllWeekCources(String id);

	List<TableCources> getAllWeekCources(String frontId, String weekId);

    Result editCources(TableCources tableCources);

    Result delete(String id);

	List<Week> getAllWeekCourcesDay(String id);

    List<StoreFront> getFront();


    Result addStore(StoreFront storeFront);

    Result editStore(StoreFront storeFront);

    List<List<List<TableCources>>> getAllWeekCourcesFront();
}
