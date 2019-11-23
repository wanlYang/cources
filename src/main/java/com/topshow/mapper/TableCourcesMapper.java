package com.topshow.mapper;

import java.util.List;

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
}