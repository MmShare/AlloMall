package com.example.allomall.utlis;

import com.example.allomall.entity.Order;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: xiapq
 * @date: 2019-06-24 13:46
 */
public class ExcelUtil {

    /**
     * 创建excel文档，
     * list 数据
     *
     * @param keys        list中map的key数组集合
     * @param keys excel的列名
     */
    public HSSFWorkbook createWorkBook(List<Order> list, String[] keys) {
        // 创建excel工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建第一个sheet页，并命名
        HSSFSheet sheet = wb.createSheet("订单统计");
//        // 设置列宽
//        for (int i = 0; i < keys.length; i++) {
//            //最后一列为附件URL地址,列宽设置大一些
//            if (i == (keys.length - 1)) {
//                sheet.setColumnWidth((short) i, (short) (200 * 120));
//            } else {
//                sheet.setColumnWidth((short) i, (short) (50 * 60));
//            }
//        }



        // 表头样式
        HSSFCellStyle cs = wb.createCellStyle();
        HSSFFont f = wb.createFont();
        f.setFontName("黑体");
        f.setFontHeightInPoints((short) 10);
        f.setBold(true);
        cs.setFont(f);
        cs.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        cs.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        cs.setLocked(true);
        cs.setWrapText(false);//自动换行


        //数据样式
        HSSFFont f2 = wb.createFont();
        f2.setFontName("黑体");
        f2.setFontHeightInPoints((short) 10);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(f2);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        cellStyle.setLocked(true);
        cellStyle.setWrapText(false);//自动换行
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 创建第一行，并设置其单元格格式
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 500);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("聚福门业");
//        cell.setCellStyle(cs);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 8));
        // 创建第二行，并设置其单元格格式
        HSSFRow row1 = sheet.createRow((short) 1);
        row1.setHeight((short) 500);
        // 单元格格式(用于列名)
        HSSFCell cell0 = row1.createCell(0);
        cell0.setCellValue("客户姓名:");
//        cell0.setCellStyle(cs);
        HSSFCell cell1 = row1.createCell(5);
        cell1.setCellValue("订单日期:");
//        cell1.setCellStyle(cs);
        for (int i=0;i<keys.length;i++){
            HSSFRow row2=sheet.createRow(2);
            HSSFCell cell4=row2.createCell(i);
            cell4.setCellValue(keys[i].toString());
//            cell4.setCellStyle(cs);
        }
        //设置首行外,每行每列的值(Row和Cell都从0开始)
        for (short i = 0; i < list.size(); i++) {
            HSSFRow rowi = sheet.createRow((short) i+3);
            //在Row行创建单元格
            for (short j = 0; j < keys.length; j++) {
                HSSFCell cellj = rowi.createCell(j);
                switch (j){
                    case 0:
                        cellj.setCellValue(i+1);
                        break;
                    case 1:
                        cellj.setCellValue(list.get(i).getHeight() == null ? " " : list.get(i).getHeight().toString());
                        break;
                    case 2:
                        cellj.setCellValue(list.get(i).getWidth() == null ? " " : list.get(i).getWidth().toString());
                        break;
                    case 3:
                        cellj.setCellValue(list.get(i).getNumber() == null ? " " : list.get(i).getNumber().toString());
                        break;
                    case 4:
                        cellj.setCellValue(list.get(i).getSquare() == null ? " " : list.get(i).getSquare().toString());
                        break;
                    case 5:
                        cellj.setCellValue(list.get(i).getHavePay() == null ? " " : list.get(i).getHavePay().toString());
                        break;
                    case 6:
                        cellj.setCellValue(list.get(i).getName() == null ? " " : list.get(i).getName().toString());
                        break;
                    case 7:
                        cellj.setCellValue(list.get(i).getAttention() == null ? " " : list.get(i).getAttention().toString());
                        break;
                    case 8:
                        cellj.setCellValue(list.get(i).getPrices());
                        break;
                    default:
                }
            }
//            //依次为每个单元格设置样式
//            for (int m = 0; m < keys.length; m++) {
//                HSSFCell hssfCell = rowi.getCell(m);
//                hssfCell.setCellStyle(cellStyle);
//            }
        }
        sheet.addMergedRegion(new CellRangeAddress(list.size()+4, list.size()+4, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(list.size()+4, list.size()+4, 7, 8));
        // 创建倒数第二行，并设置其单元格格式
        HSSFRow row_2 = sheet.createRow((short) list.size()+4);
        row_2.setHeight((short) 500);
        // 单元格格式(用于列名)
        HSSFCell cell_2_1 = row_2.createCell(0);
        cell_2_1.setCellValue("总计:");
//        cell_2_1.setCellStyle(cellStyle);
        HSSFCell cell_2_2 = row_2.createCell(7);
        cell_2_2.setCellValue("111");
//        cell_2_2.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(list.size()+5, list.size()+5, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(list.size()+5, list.size()+5, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(list.size()+5, list.size()+5, 6, 8));
        // 创建倒数第一行，并设置其单元格格式
        HSSFRow row_1 = sheet.createRow((short) list.size()+5);
        row_1.setHeight((short) 500);
        // 单元格格式(用于列名)
        HSSFCell cell_1_1 = row_1.createCell(0);
        cell_1_1.setCellValue("厂址:安徽省 天长市 邱家湾");
//        cell_1_1.setCellStyle(cellStyle);
        HSSFCell cell_1_2 = row_1.createCell(4);
        cell_1_2.setCellValue("签收日期:");
//        cell_1_2.setCellStyle(cellStyle);
        HSSFCell cell_1_3 = row_1.createCell(6);
        cell_1_3.setCellValue("客户签字:");
//        cell_1_3.setCellStyle(cellStyle);
        return wb;
    }

    //生成并下载Excel
    public void downloadWorkBook(List<Order> list, String keys[], String fileName, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            createWorkBook(list, keys).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

}
