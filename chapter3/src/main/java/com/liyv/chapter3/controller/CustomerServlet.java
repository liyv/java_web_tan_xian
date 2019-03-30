package com.liyv.chapter3.controller;

import com.liyv.framework.annotation.Action;
import com.liyv.framework.annotation.Controller;
import com.liyv.framework.bean.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller()
public class CustomerServlet {

    @Action("get:/hello")
    public View helloSmart() {
        View view = new View("hello.jsp");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(System.currentTimeMillis());
        view.addModel("currentTime", currentTime);
        return view;
    }
}
