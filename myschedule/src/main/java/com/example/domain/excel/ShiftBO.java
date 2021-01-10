package com.example.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.example.config.LocalDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @ClassName ShiftBO
 * @Description: TODO
 * @Author Aaron
 * @Date 2021/1/9
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftBO {
    @ExcelProperty("班名")
    private String shiftName;
    @ExcelProperty("数量")
    private int num;
    @ExcelProperty(value = "日期",converter = LocalDateConverter.class)
    @DateTimeFormat("yyyy/MM/dd")
    private LocalDate date;

}
