package com.example.allomall.utlis;

import com.example.allomall.entity.Order;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        // 创建excel工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建第一个sheet页，并命名
        HSSFSheet sheet = wb.createSheet("订单统计");

        //设置打印参数
//        1.边距
        sheet.setMargin(HSSFSheet.TopMargin,0);// 页边距（上）
        sheet.setMargin(HSSFSheet.BottomMargin,0);// 页边距（下）
        sheet.setMargin(HSSFSheet.LeftMargin,1 );// 页边距（左）
        sheet.setMargin(HSSFSheet.RightMargin,1);// 页边距（右
//        2.横向打印
        HSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(false);
        ps.setVResolution((short)800);
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
//        sheet.setColumnWidth(7,200 * 120);
        // 设置列宽
        for (int i = 0; i < keys.length; i++) {
            //最后一列为附件URL地址,列宽设置大一些
            if (i == (keys.length - 2)) {
                sheet.setColumnWidth((short) i, (short) (100 * 150));
            } else if (i==(keys.length-3)){
                sheet.setColumnWidth((short) i, (short) (100 * 50));
            }else {
                sheet.setColumnWidth((short) i, (short) (50 * 60));

            }
        }

        // 表头样式
        HSSFCellStyle cs = wb.createCellStyle();
        HSSFFont f = wb.createFont();
        f.setFontName("黑体");
        f.setFontHeightInPoints((short) 40);
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
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        cellStyle.setLocked(true);
        cellStyle.setWrapText(true);//自动换行
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //数据样式
        HSSFFont f3 = wb.createFont();
        f3.setFontName("黑体");
        f3.setFontHeightInPoints((short) 13);
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setFont(f3);
        cellStyle3.setLocked(true);
        cellStyle3.setWrapText(true);//自动换行
        cellStyle3.setAlignment(HorizontalAlignment.LEFT);// 左对齐
        cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

        HSSFCellStyle cellStyle4 = wb.createCellStyle();
        cellStyle4.setFont(f3);
        cellStyle4.setLocked(true);
        cellStyle4.setWrapText(true);//自动换行
        cellStyle4.setAlignment(HorizontalAlignment.CENTER);// 左对齐
        cellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        cellStyle4.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle4.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle4.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle4.setBorderRight(BorderStyle.THIN);//右边框

        // 创建第一行，并设置其单元格格式
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 900);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("聚福门业");
        cell.setCellStyle(cs);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
        // 创建第二行，并设置其单元格格式
        HSSFRow row1 = sheet.createRow((short) 1);
        row1.setHeight((short) 500);
        // 单元格格式(用于列名)
        HSSFCell cell0 = row1.createCell(0);
        cell0.setCellValue("客户姓名:"+list.get(0).getPeopleName());
        cell0.setCellStyle(cellStyle3);
        HSSFCell cell1 = row1.createCell(7);
        cell1.setCellValue("订单日期:"+sf.format(new Date()));
        cell1.setCellStyle(cellStyle3);
        HSSFRow row2=sheet.createRow(2);
        for (int i=0;i<keys.length;i++){
            HSSFCell cell4=row2.createCell(i);
            cell4.setCellValue(keys[i].toString());
            cell4.setCellStyle(cellStyle);
        }
        Double allPrices=0.00;
        //设置首行外,每行每列的值(Row和Cell都从0开始)
        for (short i = 0; i < list.size(); i++) {
            HSSFRow rowi = sheet.createRow((short) i+3);
            //在Row行创建单元格
            for (short j = 0; j < keys.length; j++) {
                HSSFCell cellj = rowi.createCell(j);
                switch (j){
                    case 0:
                        cellj.setCellValue(i+1);
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 1:
                        cellj.setCellValue(list.get(i).getHeight() == null ? "" : list.get(i).getHeight().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 2:
                        cellj.setCellValue(list.get(i).getWidth() ==null ? "" : list.get(i).getWidth().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 3:
                        cellj.setCellValue(list.get(i).getNumber() == null ? "" : list.get(i).getNumber().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 4:
                        cellj.setCellValue(list.get(i).getSquare() == null ? "" :list.get(i).getSquare().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 5:
                        cellj.setCellValue(list.get(i).getHavePay() == null ? "" : list.get(i).getHavePay().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 6:
                        cellj.setCellValue(list.get(i).getName() == null ? "" : list.get(i).getName().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 7:
                        cellj.setCellValue(list.get(i).getAttention() == null ? "" : list.get(i).getAttention().toString());
                        cellj.setCellStyle(cellStyle);
                        break;
                    case 8:
                        cellj.setCellValue(Double.valueOf(list.get(i).getPrices()));
                        cellj.setCellStyle(cellStyle);
                        break;
                    default:
                }
            }
            allPrices+=Double.valueOf(list.get(i).getPrices());
        }
        sheet.addMergedRegion(new CellRangeAddress(list.size()+3, list.size()+3, 0, 7));
//        sheet.addMergedRegion(new CellRangeAddress(list.size()+3, list.size()+3, 7, 8));
        // 创建倒数第二行，并设置其单元格格式

//        cellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFRow row_2 = sheet.createRow((short) list.size()+3);
        row_2.setHeight((short) 500);
        // 单元格格式(用于列名)
        HSSFCell cell_2_1 = row_2.createCell(0);
        cell_2_1.setCellValue("总计:");
        cell_2_1.setCellStyle(cellStyle4);
        HSSFCell cell_2_2 = row_2.createCell(1);
        cell_2_2.setCellStyle(cellStyle4);
        HSSFCell cell_2_3 = row_2.createCell(2);
        cell_2_3.setCellStyle(cellStyle4);
        HSSFCell cell_2_4 = row_2.createCell(3);
        cell_2_4.setCellStyle(cellStyle4);
        HSSFCell cell_2_5 = row_2.createCell(4);
        cell_2_5.setCellStyle(cellStyle4);
        HSSFCell cell_2_6 = row_2.createCell(5);
        cell_2_6.setCellStyle(cellStyle4);
        HSSFCell cell_2_7 = row_2.createCell(6);
        cell_2_7.setCellStyle(cellStyle4);
        HSSFCell cell_2_8 = row_2.createCell(7);
        cell_2_8.setCellStyle(cellStyle4);
        HSSFCell cell_2_9 = row_2.createCell(8);
        cell_2_9.setCellValue(allPrices);
        cell_2_9.setCellStyle(cellStyle4);


        sheet.addMergedRegion(new CellRangeAddress(list.size()+4, list.size()+4, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(list.size()+4, list.size()+4, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(list.size()+4, list.size()+4, 7, 8));
        // 创建倒数第一行，并设置其单元格格式
        HSSFRow row_1 = sheet.createRow((short) list.size()+4);
        row_1.setHeight((short) 500);
        // 单元格格式(用于列名)
        HSSFCell cell_1_1 = row_1.createCell(0);
        cell_1_1.setCellValue("厂址:安徽省 天长市 邱家湾");
        cell_1_1.setCellStyle(cellStyle3);
        HSSFCell cell_1_2 = row_1.createCell(4);
        cell_1_2.setCellValue("签收日期:");
        cell_1_2.setCellStyle(cellStyle3);
        HSSFCell cell_1_3 = row_1.createCell(7);
        cell_1_3.setCellValue("客户签字:");
        cell_1_3.setCellStyle(cellStyle3);
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
