package com.liyv.framework.bean;

/**
 * 返回 JSON 型数据对象
 */
public class Data {

    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
