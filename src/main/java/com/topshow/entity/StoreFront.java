package com.topshow.entity;

import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 门店
 * @author Administrator
 *
 */
@Alias("StoreFront")
public class StoreFront {
    
    //门店ID
    private String id;
    
    //门店名称
    private String name;

    private List<Week> weeks;

    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public StoreFront() {
    }

    public StoreFront(String id) {
        this.id = id;
    }

    public StoreFront(String id, String name, List<Week> weeks) {
        this.id = id;
        this.name = name;
        this.weeks = weeks;
    }

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

    @Override
    public String toString() {
        return "StoreFront{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", weeks=" + weeks +
                ", admin=" + admin +
                '}';
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
