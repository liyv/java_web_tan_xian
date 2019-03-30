package com.liyv.framework.bean;

import com.liyv.framework.utils.CastUtil;
import com.liyv.framework.utils.CollectionUtil;
import com.liyv.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数封装对象
 */
public class Param {

    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;


    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        if (CollectionUtil.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if (CollectionUtil.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList2;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList2 = fileMap.get(fieldName);
                } else {
                    fileParamList2 = new ArrayList<FileParam>();
                }
                fileParamList2.add(fileParam);
                fileMap.put(fieldName, fileParamList2);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 根据参数名获取 long 型参数
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFileMap().get(name));
    }

    /**
     * 根据参数名获取 double 型参数
     *
     * @param name
     * @return
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFileMap().get(name));
    }

    /**
     * 根据参数名获取 int 型参数
     *
     * @param name
     * @return
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFileMap().get(name));
    }

    /**
     * 根据参数名获取 boolean 型参数
     *
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFileMap().get(name));
    }


    /**
     * 根据参数名获取 string 型参数
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     * 验证参数是否为空
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }
}
