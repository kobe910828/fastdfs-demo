package com.hx.fastdfs.vo;

/**
 * 返回对象Vo
 */
public class ObjectRestResponse<T> extends BaseResultVo {

    T data;
    boolean rel = true;

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }


    public ObjectRestResponse rel(boolean rel) {
        setRel(rel);
        return this;
    }


    public ObjectRestResponse data(T data) {
        setData(data);
        return this;
    }

}
