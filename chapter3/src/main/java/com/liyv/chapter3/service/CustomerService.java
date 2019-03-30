package com.liyv.chapter3.service;

import com.liyv.framework.annotation.Service;
import com.liyv.framework.bean.FileParam;
import com.liyv.framework.helper.UploadHelper;

import java.util.Map;

@Service
public class CustomerService {
    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap, FileParam fileParam) {
        UploadHelper.uploadFile("/tmp/upload/", fileParam);
        return true;
    }
}
