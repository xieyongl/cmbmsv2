package com.xy.cmbms.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xieyong
 * @date 2019/12/19 - 11:47
 */
@Data
@TableName("tbl_type_goods")
public class TypeGoods implements Serializable{

    private int id; //物资类别id
    private String name;    //物资类别名称

}
