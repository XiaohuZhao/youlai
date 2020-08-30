package com.youlai.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class SysDictType extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    private Integer status;

    private  String  remark;



}
