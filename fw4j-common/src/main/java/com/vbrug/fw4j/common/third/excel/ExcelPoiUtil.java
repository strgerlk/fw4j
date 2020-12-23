package com.vbrug.fw4j.common.third.excel;

import com.vbrug.fw4j.common.util.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelPoiUtil {

    /**
     * 解析Excel，
     *
     * @param is     输入流
     * @param fields excel对应BEAN字段
     * @param clazz  对应BEAN类
     * @return
     * @throws Exception
     * @annotation (1)、默认第一行为标题，从第二行开始为数据；
     * (2)、Excel默认导入最大1500行
     */
    @SuppressWarnings("rawtypes")
    public static List parseExcel2003(InputStream is, String[] fields, Class<?> clazz) throws Exception {
        return parseExcel2003(is, fields, clazz, null, null);
    }

    /**
     * 解析Excel，
     *
     * @param is            输入流
     * @param fields        excel对应BEAN字段
     * @param clazz         对应BEAN类
     * @param startRowIndex 从第几行开始读数据
     * @param maxRows       导入最大行数限制
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List parseExcel2003(InputStream is, String[] fields, Class<?> clazz, Integer startRowIndex, Integer maxRows) throws Exception {
        // 默认初始化
        startRowIndex = (startRowIndex == null) ? 1 : startRowIndex; // 默认从第一行开始读
        maxRows = (maxRows == null) ? 1500 : maxRows;    // 默认导入最大行数1500

        List                     list = new ArrayList();

        POIFSFileSystem pfs = null;
        HSSFWorkbook    hwb = null;
        try {
            pfs = new POIFSFileSystem(is);
            hwb = new HSSFWorkbook(pfs);
            HSSFSheet sheet = hwb.getSheetAt(0);

            // 处理标题
            Map<String, PropertyDescriptor> pdsMap = new HashMap<>();
            HSSFRow row = sheet.getRow(0);
            List<String> titleList = new ArrayList<>();
            if (row == null || row.getLastCellNum() == 0)
                return null;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i) == null)
                    continue;
                PropertyDescriptor titlePds = getTitlePds(row.getCell(i).getStringCellValue(), fields, clazz);
                if (titlePds != null)
                    pdsMap.put(String.valueOf(i), titlePds);

            }

            for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
                if (i > 1500) {
                    throw new Exception("1500: 导入数据过多，最多可导入" + maxRows + "行！");
                }
                row = sheet.getRow(i);
                Object obj = handleHSSFRow(row, clazz.newInstance(), pdsMap);
                if (obj != null)
                    list.add(obj);
            }
            return list;
        } catch (OfficeXmlFileException e) {
            throw new Exception("1501: 不支持office2007及以上版本！");
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    /**
     * 处理一行数据
     *
     * @param row
     * @param obj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    public static Object handleHSSFRow(HSSFRow row, Object obj, Map<String, PropertyDescriptor> pdsMap) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (row == null
                || row.getLastCellNum() == 0
                || row.getCell(0) == null
                || StringUtils.isEmpty(row.getCell(0).getStringCellValue()))
            return null;
        HSSFCell         cell;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < row.getLastCellNum(); i++) {
            cell = row.getCell(i);
            if (cell == null)
                continue;
            String value = "";
            switch (cell.getCellType()) {
                /*case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        HSSFDataFormatter dft = new HSSFDataFormatter();
                        value = dft.formatCellValue(cell);
                    }
                    break;*/
                default:
                    value = cell.getStringCellValue().trim();
                    break;
            }
            if (pdsMap.get(String.valueOf(i)) != null)
                pdsMap.get(String.valueOf(i)).getWriteMethod().invoke(obj, value);
        }
        return obj;
    }

    /**
     * 解析Excel，
     *
     * @param is            输入流
     * @param fields        excel对应BEAN字段
     * @param clazz         对应BEAN类
     * @param startRowIndex 从第几行开始读数据
     * @param maxRows       导入最大行数限制
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List parseExcel2007(InputStream is, String[] fields, Class<?> clazz, Integer startRowIndex, Integer maxRows) throws Exception {
        // 默认初始化
        startRowIndex = (startRowIndex == null) ? 1 : startRowIndex; // 默认从第一行开始读
        maxRows = (maxRows == null) ? 1500 : maxRows;    // 默认导入最大行数1500

        List                     list = new ArrayList();

        XSSFWorkbook xwb = null;
        try {
            xwb = new XSSFWorkbook(is);
            XSSFSheet sheet = xwb.getSheetAt(0);

            // 处理标题
            Map<String, PropertyDescriptor> pdsMap = new HashMap<>();
            XSSFRow row = sheet.getRow(0);
            List<String> titleList = new ArrayList<>();
            if (row == null || row.getLastCellNum() == 0)
                return null;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i) == null)
                    continue;
                PropertyDescriptor titlePds = getTitlePds(row.getCell(i).getStringCellValue(), fields, clazz);
                if (titlePds != null)
                    pdsMap.put(String.valueOf(i), titlePds);

            }

            for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
                if (i > 1500) {
                    throw new Exception("1500: 导入数据过多，最多可导入" + maxRows + "行！");
                }
                row = sheet.getRow(i);
                Object obj = handleXSSFRow(row, clazz.newInstance(), pdsMap);
                if (obj != null)
                    list.add(obj);
            }
            return list;
        } catch (OfficeXmlFileException e) {
            throw new Exception("1501: 不支持office2003及以下版本！");
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    private static PropertyDescriptor getTitlePds(String title, String[] fields, Class<?> clazz) throws IntrospectionException {
        for (String field : fields) {
            String[] strings = field.split(":");
            if(title.contains(strings[1]))
                return  new PropertyDescriptor(strings[0], clazz);
        }
        return null;
    }

    /**
     * 处理一行数据
     *
     * @param row
     * @param obj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    public static Object handleXSSFRow(XSSFRow row, Object obj, Map<String, PropertyDescriptor> pdsMap) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (row == null
                || row.getLastCellNum() == 0
                || row.getCell(0) == null
                || StringUtils.isEmpty(row.getCell(0).getStringCellValue()))
            return null;
        XSSFCell cell;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < row.getLastCellNum(); i++) {
            cell = row.getCell(i);
            if (cell == null)
                continue;
            String value = "";
            switch (cell.getCellType()) {
                /*case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        HSSFDataFormatter dft = new HSSFDataFormatter();
                        value = dft.formatCellValue(cell);
                    }
                    break;*/
                default:
                    value = cell.getStringCellValue().trim();
                    break;
            }
            if (pdsMap.get(String.valueOf(i)) != null)
                pdsMap.get(String.valueOf(i)).getWriteMethod().invoke(obj, value);
        }
        return obj;
    }


    /**
     * 多LIst导出Excel
     *
     * @param workbook
     * @param dataList
     * @param fields
     * @throws Exception
     */
    public static void writeMutilListExcel(HSSFWorkbook workbook,
                                           HSSFSheet sheet, List<?> dataList, String[] fields,
                                           int startRowIndex) throws Exception {
        HSSFCellStyle doubStyle = getDoubleStyle(workbook);
        HSSFCellStyle intStyle  = getIntegerStyle(workbook);

        // 得到属性描述器
        List<PropertyDescriptor> pds = getTitleDesc(dataList, fields, sheet,
                getTitleStyle(workbook), startRowIndex);

        for (int i = 0; i < dataList.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1 + startRowIndex);
            for (int j = 0; j < fields.length; j++) {
                Object obj = pds.get(j).getReadMethod().invoke(dataList.get(i));
                setConvCell(row.createCell((short) j), obj, doubStyle, intStyle);
            }
        }
    }


    /**
     * 导出Excel
     *
     * @param os
     * @param dataList
     * @param fields
     * @throws Exception
     */
    public static void writeExcel(OutputStream os, List<?> dataList,
                                  String[] fields) throws Exception {


        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();

            HSSFCellStyle doubStyle = getDoubleStyle(workbook);
            HSSFCellStyle intStyle  = getIntegerStyle(workbook);

            HSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultColumnWidth(17);

            // 得到属性描述器
            List<PropertyDescriptor> pds = getTitleDesc(dataList, fields, sheet, getTitleStyle(workbook), 0);

            for (int i = 0; i < dataList.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < fields.length; j++) {
                    Object obj = pds.get(j).getReadMethod()
                            .invoke(dataList.get(i));
                    setConvCell(row.createCell((short) j), obj, doubStyle, intStyle);
                }
            }
            workbook.write(os);
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    /**
     * 基于模板导出Excel
     *
     * @param os
     * @param dataList
     * @param fields
     * @param modelPath
     * @throws Exception
     */
    public static void writeExlBaseModel(OutputStream os, List<?> dataList,
                                         String[] fields, String modelPath, int startRowIndex) throws Exception {
        // 得到属性描述器
        List<PropertyDescriptor> pds = getDescriptor(dataList, fields);

        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new POIFSFileSystem(
                    new FileInputStream(modelPath)));

            HSSFCellStyle doubStyle = getDoubleStyle(workbook);
            HSSFCellStyle intStyle  = getIntegerStyle(workbook);

            HSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < dataList.size(); i++) {
                HSSFRow row = sheet.createRow(i + startRowIndex - 1);
                for (int j = 0; j < fields.length; j++) {
                    Object obj = pds.get(j).getReadMethod()
                            .invoke(dataList.get(i));
                    setConvCell(row.createCell((short) j), obj, doubStyle, intStyle);
                }
            }
            workbook.write(os);
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }


    /**
     * 设置excel格式
     *
     * @return
     */
    public static void setConvCell(HSSFCell cell, Object obj,
                                   HSSFCellStyle doubCellStyle, HSSFCellStyle intCellStyle) {
        if (obj == null) {
            cell.setCellValue(new HSSFRichTextString(""));
        } else if (obj.getClass() == Double.class) {
            cell.setCellValue((Double) obj);
            cell.setCellStyle(doubCellStyle);
        } else if (obj.getClass() == Integer.class) {
            cell.setCellValue((Integer) obj);
            cell.setCellStyle(intCellStyle);
        } else {
            cell.setCellValue(new HSSFRichTextString(obj.toString()));
        }
    }

    /**
     * 设置保留两位小数样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getDoubleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        return cellStyle;
    }

    /**
     * 设置整数样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getIntegerStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        return cellStyle;
    }

    /**
     * 设置标题样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = workbook.createFont();
//        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        return cellStyle;
    }


    /**
     * 得到bean属性描述器
     *
     * @param dataList
     * @param fields
     * @return
     * @throws IntrospectionException
     */
    public static List<PropertyDescriptor> getDescriptor(List<?> dataList,
                                                         String[] fields) throws IntrospectionException {
        List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
        if (dataList != null && dataList.size() > 0) {
            Class<?> clazz = dataList.get(0).getClass();
            for (int i = 0; i < fields.length; i++) {
                list.add(new PropertyDescriptor(fields[i], clazz));
            }
        }
        return list;
    }

    /**
     * 设置Excel标题并得到bean属性描述器
     *
     * @param dataList
     * @param fields
     * @return
     * @throws IntrospectionException
     */
    public static List<PropertyDescriptor> getTitleDesc(List<?> dataList,
                                                        String[] fields, HSSFSheet sheet, HSSFCellStyle titleStyle, int startIndex) throws IntrospectionException {
        List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
        if (dataList != null && dataList.size() > 0) {
            Class<?> clazz   = dataList.get(0).getClass();
            String[] loopArr = null;
            HSSFRow  row     = sheet.createRow(startIndex);
            for (int i = 0; i < fields.length; i++) {
                loopArr = fields[i].split(":");
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(loopArr[0]);
                cell.setCellStyle(titleStyle);
                list.add(new PropertyDescriptor(loopArr[1], clazz));
            }
        }
        return list;
    }

    /**
     * 得到bean属性描述器
     *
     * @param fields
     * @return
     * @throws IntrospectionException
     */
    public static List<PropertyDescriptor> getDescriptor(Class<?> clazz,
                                                         String[] fields) throws IntrospectionException {
        List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
        for (int i = 0; i < fields.length; i++) {
            list.add(new PropertyDescriptor(fields[i], clazz));
        }
        return list;
    }

}