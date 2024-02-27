package com.yuesheng.pm.util;

import com.yuesheng.pm.annotation.CellLink;
import com.yuesheng.pm.annotation.Excel;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.ProMaterial;
import com.yuesheng.pm.model.Cell;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
public class EntityUtils<T> extends Object {
    /**
     * 获取表格行数据
     *
     * @param entityData
     * @param valueNames
     * @return
     */
    public static List<Cell> getCells(Object entityData, String[] valueNames) {
        ArrayList<Cell> arrayList = new ArrayList<>();
        for (int f = 0; f < valueNames.length; f++) {
            Cell cell = new Cell();
            cell.setIndex(f);
            String fieldName = valueNames[f];
            //获取属性值
            Excel excel = getAno(fieldName, entityData.getClass(), Excel.class);
            Object object = EntityUtils.getFieldValue(fieldName, entityData);
            if (Objects.isNull(object)) {
                if (excel != null) {
                    if (StringUtils.isNotBlank(excel.coverFormat())) {
                        cell.setName(format(excel, object));
                    }
                    cell.setRowIndex(excel.index());
                } else {
                    cell.setName("");
                }
            } else {
                //是否格式化
                if (excel != null) {
                    if (StringUtils.isNotBlank(excel.coverFormat())) {
                        cell.setName(format(excel, object));
                    }
                    cell.setRowIndex(excel.index());
                    cell.setNoBreak(excel.noBreak());
                }
                if (StringUtils.isBlank(cell.getName())) {
                    if (object instanceof String) {
                        cell.setName(object.toString());
                    } else if (object instanceof Double) {
                        cell.setName(((Double) object) + "");
                    } else if (object instanceof Integer) {
                        cell.setName(((Integer) object) + "");
                    } else if (object instanceof Date) {
                        cell.setName(DateUtil.format((Date) object, "yyyy-MM-dd"));
                    } else if (object instanceof BigDecimal) {
                        cell.setName(String.valueOf(object));
                    }
                }
            }

            //设置注解标记
            setLinkFlag(cell, fieldName, entityData);

            arrayList.add(cell);
        }

        return arrayList;
    }

    private static String format(Excel excel, Object object) {
        String format = excel.coverFormat();
        String[] fs = format.split(",");
        String result = "";
        String value = String.valueOf(object);
        for (int i = 0; i < fs.length; i++) {
            String[] km = fs[i].split("=");
            if (StringUtils.equals(value, km[0])) {
                result = km[1];
                break;
            }
        }
        return result;
    }

    private static void setLinkFlag(Cell cell, String fieldName, Object entityData) {

        try {
            Method method = null;
            method = EntityUtils.getMethod("get" + fieldName, entityData);
            CellLink link = method.getAnnotation(CellLink.class);
            if (!Objects.isNull(link) && link.link()) {
                cell.setLink(true);
            }
        } catch (NoSuchMethodException e) {

        }
    }

    private static Method getMethod(String methodName, Object source) throws NoSuchMethodException {
        return source.getClass().getMethod(methodName);
    }

    public static Object getFieldValue(String fieldName, Object source) {
        String[] split = fieldName.split("\\.");
        if (split.length > 1) {
            Object target = source;
            for (int x = 0; x < split.length; x++) {
                target = getFieldValue(split[x], target);
            }
            return target;
        } else {
            try {
                return ReflectionUtils.invokeMethod(getMethod("get" + fieldName, source), source);
            } catch (NullPointerException | NoSuchMethodException e) {
                return null;
            }
        }
    }

    private static Field getFieldObject(String fieldName, Class target) {
        if (target.getName().equals(Object.class.getName())) {
            return null;
        } else {
            Field field = null;
            try {
                field = target.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {

            }
            if (Objects.isNull(field)) {
                return getFieldObject(fieldName, target.getSuperclass());
            } else {
                return field;
            }
        }
    }

    public static <T> T getAno(String fieldName, Class source, Class annoClass) {
        String[] fieldArray = fieldName.split("\\.");
        Class cs = source;
        Field os = null;
        String lastField = "";
        for (int i = 0; i < fieldArray.length; i++) {
            lastField = StringUtils.uncapitalize(fieldArray[i]);
            try {
                if (i < (fieldArray.length - 1)) {
                    cs = getFieldObject(lastField, cs).getType();
                } else {
                    os = getFieldObject(lastField, cs);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        if (!Objects.isNull(os)) {
            os.setAccessible(true);
            return (T) os.getAnnotation(annoClass);
        }
        return null;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        ProMaterial pm = new ProMaterial();
        Material m = new Material();
        m.setName("");
        pm.setMaterial(m);
        Excel excel = getAno("Material.Name", pm.getClass(), Excel.class);
        System.out.println(excel.name());
    }

}
