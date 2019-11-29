package com.topshow.entity;

import org.apache.ibatis.type.Alias;

@Alias("TableImages")
public class TableImages {

    private String id;
    private String src;
    private StoreFront storeFront;

    public StoreFront getStoreFront() {
        return storeFront;
    }

    public void setStoreFront(StoreFront storeFront) {
        this.storeFront = storeFront;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "TableImages{" +
                "id='" + id + '\'' +
                ", src='" + src + '\'' +
                ", storeFront=" + storeFront +
                '}';
    }
}
