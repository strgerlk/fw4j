package com.vbrug.fw4j.common.third.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ExcelParserXLS<T> implements ExcelParser<T> {

    /*
     * 开始读取行数
     */
    private int startRowIndex;

    /*
     * 文件读取最大行数
     */
    private int maxRows;

    @Override
    public List<Map<String, String>> parseExcel(InputStream is) throws Exception {
//        return parseExcel(is, Map<String, String>.class);
        return null;
    }

    @Override
    public List<T> parseExcel(InputStream is, Class<T> clazz) throws Exception {
        return null;
    }

    @Override
    public List<T> parseExcel(InputStream is, String[] fields, Class<T> clazz) throws Exception {
        return null;
    }

    @Override
    public List<T> parseExcel(InputStream is, String[] fields, Class<T> clazz, int startRowIndex, int maxRows) throws Exception {
        return null;
    }


//    /**
//     * 解析Excel，
//     * @param is     输入流
//     * @param fields excel对应BEAN字段
//     * @param clazz  对应BEAN类
//     * @return
//     * @throws Exception
//     * @annotation (1)、默认第一行为标题，从第二行开始为数据；
//     * (2)、Excel默认导入最大1500行
//     */
//    @SuppressWarnings("rawtypes")
//    public static List parseExcel2003(InputStream is, String[] fields, Class<?> clazz) throws Exception {
//        return parseExcel2003(is, fields, clazz, null, null);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static List parseExcel2003(InputStream is, String[] fields, Class<?> clazz, Integer startRowIndex, Integer maxRows) throws Exception {
//        // 默认初始化
//        startRowIndex = (startRowIndex == null) ? 1 : startRowIndex; // 默认从第一行开始读
//        maxRows = (maxRows == null) ? 1500 : maxRows;    // 默认导入最大行数1500
//
//        List list = new ArrayList();
//
//        POIFSFileSystem pfs = null;
//        HSSFWorkbook    hwb = null;
//        try {
//            pfs = new POIFSFileSystem(is);
//            hwb = new HSSFWorkbook(pfs);
//            HSSFSheet sheet = hwb.getSheetAt(0);
//
//            // 处理标题
//            Map<String, PropertyDescriptor> pdsMap    = new HashMap<>();
//            HSSFRow                         row       = sheet.getRow(0);
//            List<String>                    titleList = new ArrayList<>();
//            if (row == null || row.getLastCellNum() == 0)
//                return null;
//            for (int i = 0; i < row.getLastCellNum(); i++) {
//                if (row.getCell(i) == null)
//                    continue;
//                PropertyDescriptor titlePds = getTitlePds(row.getCell(i).getStringCellValue(), fields, clazz);
//                if (titlePds != null)
//                    pdsMap.put(String.valueOf(i), titlePds);
//
//            }
//
//            for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
//                if (i > 1500) {
//                    throw new Exception("1500: 导入数据过多，最多可导入" + maxRows + "行！");
//                }
//                row = sheet.getRow(i);
//                Object obj = handleHSSFRow(row, clazz.newInstance(), pdsMap);
//                if (obj != null)
//                    list.add(obj);
//            }
//            return list;
//        } catch (OfficeXmlFileException e) {
//            throw new Exception("1501: 不支持office2007及以上版本！");
//        } catch (Exception e) {
//            throw e;
//        } finally {
//        }
//    }
//
//
//    /**
//     * 处理一行数据
//     * @param row
//     * @param obj
//     * @return
//     * @throws InvocationTargetException
//     * @throws IllegalArgumentException
//     * @throws IllegalAccessException
//     */
//    public static Object handleHSSFRow(HSSFRow row, Object obj, Map<String, PropertyDescriptor> pdsMap) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        if (row == null
//                || row.getLastCellNum() == 0
//                || row.getCell(0) == null
//                || StringUtils.isEmpty(row.getCell(0).getStringCellValue()))
//            return null;
//        HSSFCell         cell;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (int i = 0; i < row.getLastCellNum(); i++) {
//            cell = row.getCell(i);
//            if (cell == null)
//                continue;
//            String value = "";
//            switch (cell.getCellType()) {
//                /*case Cell.CELL_TYPE_NUMERIC:
//                    if (DateUtil.isCellDateFormatted(cell)) {
//                        value = sdf.format(cell.getDateCellValue());
//                    } else {
//                        HSSFDataFormatter dft = new HSSFDataFormatter();
//                        value = dft.formatCellValue(cell);
//                    }
//                    break;*/
//                default:
//                    value = cell.getStringCellValue().trim();
//                    break;
//            }
//            if (pdsMap.get(String.valueOf(i)) != null)
//                pdsMap.get(String.valueOf(i)).getWriteMethod().invoke(obj, value);
//        }
//        return obj;
//    }
}
