package com.example.controller.zuoxi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSON;
import com.example.domain.excel.ShiftBO;
import com.example.listener.ShiftExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExcelController
 * @Description: TODO
 * @Author Aaron
 * @Date 2021/1/9
 * @Version V1.0
 **/
@RestController
@RequestMapping("/shiftExcel")
public class ExcelController {
    @Autowired
    private ShiftExcelListener shiftExcelListener;
    @PostMapping("/upload")
    public String upload(MultipartFile multipartFile) throws IOException {
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(multipartFile.getInputStream(), ShiftBO.class, shiftExcelListener);
        //doXXX自动关闭流
        excelReaderBuilder.sheet().doRead();
        return "suc";
    }
    @GetMapping("/download")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            //int i = 1/0;
            EasyExcel.write(response.getOutputStream(), ShiftBO.class).sheet("模板")
                    .doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }
    @GetMapping("/downloadFillExcel")
    public void downloadFillExcel(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试11", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            //ShiftBO bo = new ShiftBO("A1", 3, LocalDate.now());
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ShiftBO.class).withTemplate("downloadTemplate.xlsx").sheet()
                    .doFill(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    private List data() {
        List<ShiftBO> list= new ArrayList();
        for (int i = 0; i <10 ; i++) {
            ShiftBO bo = new ShiftBO();
            bo.setShiftName("A"+i);
            bo.setNum(i);
            bo.setDate(LocalDate.now());
            list.add(bo);
        }
        return list;
    }
}
