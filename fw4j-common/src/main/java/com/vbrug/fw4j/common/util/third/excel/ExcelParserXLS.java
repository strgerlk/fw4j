package com.vbrug.fw4j.common.util.third.excel;

import com.vbrug.fw4j.common.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * XLS文件解析
 * @author vbrug
 * @since 1.0.0
 */
public class ExcelParserXLS extends BaseExcelParser implements ExcelParser {

    @Override
    public List<Map<String, String>> parseExcel(InputStream is) throws Exception {
        return this.parseExcel(is, sheetIndex, startRowIndex, maxRows);
    }

    @Override
    public List<Map<String, String>> parseExcel(InputStream is, String... fields) throws Exception {
        return this.parseExcel(is, sheetIndex, startRowIndex, maxRows, fields);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<Map<String, String>> parseExcel(InputStream is, int sheetIndex, int startRowIndex, int maxRows, String... fields) throws Exception {
        List<Map> maps = this.parseExcel(is, Map.class, sheetIndex, startRowIndex, maxRows, fields);
        return maps.stream().map(x -> (Map<String, String>) x).collect(Collectors.toList());
    }

    @Override
    public <T> List<T> parseExcel(InputStream is, Class<T> clazz) throws Exception {
        return this.parseExcel(is, clazz, sheetIndex, startRowIndex, maxRows);
    }

    @Override
    public <T> List<T> parseExcel(InputStream is, Class<T> clazz, String... fields) throws Exception {
        return this.parseExcel(is, clazz, sheetIndex, startRowIndex, maxRows, fields);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <T> List<T> parseExcel(InputStream is, Class<T> clazz, int sheetIndex, int startRowIndex, int maxRows, String... fields) throws Exception {
        // 默认初始化
        List<T>         resultList = new ArrayList();
        POIFSFileSystem pfs        = null;
        HSSFWorkbook    hwb        = null;
        try {
            pfs = new POIFSFileSystem(is);
            hwb = new HSSFWorkbook(pfs);
            HSSFSheet sheet = hwb.getSheetAt(sheetIndex);
            // 01-处理标题
            List<Object> fieldList = new ArrayList<>();
            if (ObjectUtils.isNull(fields)) {
                fields = handleHSSFRow(sheet.getRow(startRowIndex), 0).toArray(new String[0]);
                if (ObjectUtils.isNull(fields))
                    throw new Exception("1502：标题为空");
                startRowIndex++;
            }
            if (clazz == Map.class) {
                Arrays.stream(fields).forEach(fieldList::add);
            } else {
                Arrays.stream(fields).forEach(x -> {
                    fieldList.add(ClassUtils.getPropertyDescriptor(clazz, x));
                });
            }

            // 02-读取数据
            HSSFRow loopRow;
            for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
                if (i > maxRows) {
                    throw new Exception("1500: 导入数据过多，最多可导入" + maxRows + "行！");
                }
                List<String> valueList = handleHSSFRow(sheet.getRow(i), fieldList.size());
                if (CollectionUtils.isEmpty(valueList)) {
                    if (blankStop) break;
                    continue;
                }
                if (clazz == Map.class) {
                    Map<String, String> valueMap = new HashMap<>();
                    for (int k = 0; k < fieldList.size() && k < valueList.size(); k++) {
                        String field = String.valueOf(fieldList.get(k));
                        if (!StringUtils.isEmpty(field))
                            valueMap.put(field, valueList.get(k));
                    }
                    resultList.add((T) valueMap);
                } else {
                    T valueBean = clazz.newInstance();
                    for (int k = 0; k < fieldList.size() && k < valueList.size(); k++) {
                        PropertyDescriptor pds = (PropertyDescriptor) fieldList.get(k);
                        if (ObjectUtils.notNull(pds))
                            pds.getWriteMethod().invoke(valueBean, valueList.get(k));
                    }
                    resultList.add(valueBean);
                }
            }
        } catch (OfficeXmlFileException e) {
            throw new Exception("1501: 不支持office2007及以上版本！");
        } finally {
            if (pfs != null) pfs.close();
            if (hwb != null) hwb.close();
        }
        return resultList;
    }


    /**
     * 将一行数据转为字符串
     */
    private static List<String> handleHSSFRow(HSSFRow row, int maxColumnNumber) throws Exception {
        // 空行舍弃
        if (row == null || row.getLastCellNum() == 0)
            return null;
        List<String> resultList = new ArrayList<>();
        HSSFCell     loopCell;
        maxColumnNumber = (maxColumnNumber == 0) ? row.getLastCellNum() : maxColumnNumber;
        for (int i = 0; i < row.getLastCellNum() && i < maxColumnNumber; i++) {
            loopCell = row.getCell(i);
            String value = "";
            if (loopCell == null)
                continue;
            switch (loopCell.getCellType()) {
                case _NONE:
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(loopCell)) {
                        value = new SimpleDateFormat(DateUtils.YYYY_MM_DD).format(loopCell.getDateCellValue());
                    } else {
                        HSSFDataFormatter dft = new HSSFDataFormatter();
                        value = dft.formatCellValue(loopCell);
                    }
                    break;
                case STRING:
                case FORMULA:
                case BLANK:
                case BOOLEAN:
                case ERROR:
                default:
                    value = loopCell.getStringCellValue().trim();
                    break;
            }
            resultList.add(value);
        }
        return resultList;
    }

}
