package com.example.convert;

import com.example.domain.excel.ShiftBO;
import com.example.domain.zuoxi.bean.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShiftConverter {
    ShiftConverter INSTANCE= Mappers.getMapper(ShiftConverter.class);
    @Mapping(target = "week",constant = "1")
    @Mapping(source = "date",target = "date")
    @Mapping(target = "shiftName",source = "shiftName",defaultValue = "1")
    Shift bo2do(ShiftBO shiftBO);
    /*@Mappings({
            @Mapping(target = "userName",source = "name"),
            //不同类型转换
            @Mapping(target = "status",expression = "java(java.lang.String.valueOf(user.getStatus()))"),
            //Date转换String
            @Mapping(target = "createTime",dateFormat="yyyy-MM-dd HH:mm")
    })*/
    ShiftBO do2bo(Shift shift);
    List<ShiftBO> dolist2bolist(List<Shift> shiftlist);
    List<Shift> bolist2dolist(List<ShiftBO> shiftbolist);
}
