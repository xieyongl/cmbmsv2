package com.xy.cmbms.entity.vos;

import lombok.Data;

/**
 * @author Xieyong
 * @date 2020/3/4 - 10:11
 */
@Data
public class ExcelExpVo {
    private String sheetName;
//    private String[] handers;
//    private List<String[]> dateset;

    public ExcelExpVo(String sheetName) {
        this.sheetName = sheetName;
//        this.handers = handers;
//        this.dateset = dataset;
    }
}
