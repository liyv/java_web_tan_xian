package com.liyv.chapter3.controller;

import com.liyv.chapter3.service.CustomerService;
import com.liyv.framework.annotation.Action;
import com.liyv.framework.annotation.Controller;
import com.liyv.framework.annotation.Inject;
import com.liyv.framework.bean.Data;
import com.liyv.framework.bean.FileParam;
import com.liyv.framework.bean.Param;
import com.liyv.framework.bean.View;

import java.util.Map;

@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;


    @Action("get:/create_customer")
    public View createCustomer() {
        View view = new View("customer_create.jsp");
        return view;
    }

    /**
     * 处理 创建客户 请求
     */
    @Action("post:/create_customer")
    public Data createSubmit(Param param) {
        Map<String, Object> fieldMap = param.getFieldMap();
        FileParam fileParam = param.getFile("photo");
        boolean result = customerService.createCustomer(fieldMap, fileParam);
        return new Data(result);
    }
}
