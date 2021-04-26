package com.vbrug.fw4j.common.third.excel;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ExcelParserXLSX {

//    /**
//     * 解析Excel，
//     *
//     * @param is            输入流
//     * @param fields        excel对应BEAN字段
//     * @param clazz         对应BEAN类
//     * @param startRowIndex 从第几行开始读数据
//     * @param maxRows       导入最大行数限制
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static List parseExcel2007(InputStream is, String[] fields, Class<?> clazz, Integer startRowIndex, Integer maxRows) throws Exception {
//        // 默认初始化
//        startRowIndex = (startRowIndex == null) ? 1 : startRowIndex; // 默认从第一行开始读
//        maxRows = (maxRows == null) ? 1500 : maxRows;    // 默认导入最大行数1500
//
//        List                     list = new ArrayList();
//
//        XSSFWorkbook xwb = null;
//        try {
//            xwb = new XSSFWorkbook(is);
//            XSSFSheet sheet = xwb.getSheetAt(0);
//
//            // 处理标题
//            Map<String, PropertyDescriptor> pdsMap = new HashMap<>();
//            XSSFRow row = sheet.getRow(0);
//            List<String> titleList = new ArrayList<>();
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
//                Object obj = handleXSSFRow(row, clazz.newInstance(), pdsMap);
//                if (obj != null)
//                    list.add(obj);
//            }
//            return list;
//        } catch (OfficeXmlFileException e) {
//            throw new Exception("1501: 不支持office2003及以下版本！");
//        } catch (Exception e) {
//            throw e;
//        } finally {
//
//        }
//    }
//
//
//
//    /**
//     * 解析Excel，
//     *
//     * @param is            输入流
//     * @param fields        excel对应BEAN字段
//     * @param clazz         对应BEAN类
//     * @param startRowIndex 从第几行开始读数据
//     * @param maxRows       导入最大行数限制
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static List<Map<String, String>> parseExcel2007(InputStream is, String[] fields, Integer startRowIndex, Integer maxRows) throws Exception {
//        // 默认初始化
//        startRowIndex = (startRowIndex == null) ? 1 : startRowIndex; // 默认从第一行开始读
//        maxRows = (maxRows == null) ? 1500 : maxRows;    // 默认导入最大行数1500
//
//        List                     list = new ArrayList();
//
//        XSSFWorkbook xwb = null;
//        try {
//            xwb = new XSSFWorkbook(is);
//            XSSFSheet sheet = xwb.getSheetAt(0);
//
//            // 处理标题
//            Map<String, PropertyDescriptor> pdsMap = new HashMap<>();
//            XSSFRow row = sheet.getRow(0);
//            List<String> titleList = new ArrayList<>();
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
//                Object obj = handleXSSFRow(row, clazz.newInstance(), pdsMap);
//                if (obj != null)
//                    list.add(obj);
//            }
//            return list;
//        } catch (OfficeXmlFileException e) {
//            throw new Exception("1501: 不支持office2003及以下版本！");
//        } catch (Exception e) {
//            throw e;
//        } finally {
//
//        }
//    }
}
