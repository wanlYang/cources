package com.topshow.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("TableCources")
public class TableCources {
	
	//课程ID
	private String id;
	//课程名称
	private String name;
	//课程星级
	private String star_class;
	//舞蹈类型
	private String type;
	//舞蹈作用
	private String effect;
	//开始时间
	private String start_time;
	//结束时间
	private String end_time;
	//对应星期
	private Week week;
	
	//门店ID
	private StoreFront storeFront;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStar_class() {
		return star_class;
	}
	public void setStar_class(String star_class) {
		this.star_class = star_class;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
    public Week getWeek() {
        return week;
    }
    public void setWeek(Week week) {
        this.week = week;
    }
    public StoreFront getStoreFront() {
        return storeFront;
    }
    public void setStoreFront(StoreFront storeFront) {
        this.storeFront = storeFront;
    }
    @Override
    public String toString() {
        return "TableCources [id=" + id + ", name=" + name + ", star_class=" + star_class + ", type=" + type
                + ", effect=" + effect + ", start_time=" + start_time + ", end_time=" + end_time + ", week=" + week
                + ", storeFront=" + storeFront + "]";
    }

	public TableCources(String id, String name, String star_class, String type, String effect, String start_time, String end_time, Week week, StoreFront storeFront) {
		this.id = id;
		this.name = name;
		this.star_class = star_class;
		this.type = type;
		this.effect = effect;
		this.start_time = start_time;
		this.end_time = end_time;
		this.week = week;
		this.storeFront = storeFront;
	}

	public TableCources() {
	}
}
