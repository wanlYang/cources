package com.topshow.entity;

import org.apache.ibatis.type.Alias;

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
        return "StoreFront [id=" + id + ", name=" + name + "]";
    }
    
}
