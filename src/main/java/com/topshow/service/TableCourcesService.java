package com.topshow.service;

import java.util.List;

import com.topshow.entity.*;

public interface TableCourcesService {

	List<Week> getWeek();

	Result addCources(TableCources tableCources);

    List<List<TableCources>> getAllWeekCources(String id);

	List<TableCources> getAllWeekCources(String frontId, String weekId);

    Result editCources(TableCources tableCources);

    Result delete(String id);

	List<StoreFront> getAllWeekCourcesDay();

    List<StoreFront> getFront(String admin_id);


    Result addStore(StoreFront storeFront);

    Result editStore(StoreFront storeFront);

    List<List<List<TableCources>>> getAllWeekCourcesFront();

    boolean init(String StoreId);

    List<Admin> getAllAdmin();

    StoreFront getOneFront(String admin_id);

    StoreFront findStoreById(String store);

    Integer insertStoreForAdmin(String storeId, String adminId);

    void updateStoreForAdmin(String storeId, String adminId);

    List<TableImages> getImagesList(String admin_id);

    Integer addCourcesImages(TableImages tableImages);

    Integer deleteImages(String id);

    List<TableImages> getAllWeekCourcesDayImg();
}
