package com.topshow.mapper;

import java.util.List;

import com.topshow.entity.TableImages;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.topshow.entity.StoreFront;
import com.topshow.entity.TableCources;
import com.topshow.entity.Week;

@Repository
public interface TableCourcesMapper {

	List<Week> getWeek();

    Week getWeekById(String id);

    StoreFront getStoreFrontById(String id);

    Integer addCources(TableCources tableCources);

    List<TableCources> findAllTableCourcesByStoreId(String id);

    List<TableCources> findAllTableCourcesByWeekId(String id);
    
    List<Week> findAllWeekCources(String id);

	List<TableCources> findCourcesByFrontIdAndWeekId(@Param("frontId")String frontId, @Param("weekId")String weekId);

	TableCources findCourcesById(String id);

    Integer update(TableCources tableCources);

    Integer delete(String id);

    List<StoreFront> finAllFront();

    Integer creatStore(StoreFront storeFront);

    Integer updateStore(StoreFront storeFront);

    List<StoreFront> finAllFrontByAdminId(String admin_id);

    StoreFront findFrontByAdminId(String admin_id);

    Integer insertStoreForAdmin(@Param("storeId") String storeId,@Param("adminId")String adminId);

    Integer updateStoreForAdmin(@Param("storeId") String storeId, @Param("adminId") String adminId);

    List<TableImages> findTableImages();

    Integer creatCourcesImages(TableImages tableImages);

    List<TableImages> findTableImagesByStoreId(String id);

    Integer deleteImages(String id);

    Integer creatCourcesImagesByStore(StoreFront storeFront);

    List<TableImages> findAllCourcesImg();
}
