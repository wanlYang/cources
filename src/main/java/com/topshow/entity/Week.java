package com.topshow.entity;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("Week")
public class Week {
	
	//ID
	private String id;
	
	//描述
	private String describe;
	
	private String english;
	
	private List<TableCources> tableCources;

	public Week(String id) {
		this.id = id;
	}

	public Week() {
	}

	public List<TableCources> getTableCources() {
        return tableCources;
    }

    public void setTableCources(List<TableCources> tableCources) {
        this.tableCources = tableCources;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}


	@Override
	public String toString() {
		return "Week [id=" + id + ", describe=" + describe + ", english=" + english + ", tableCources=" + tableCources
				+ "]";
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	

}
