package com.vbrug.fw4j.common.util;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ObjectUtilsBean {

    private Long startNumber;
    private Long endNumber;

    public ObjectUtilsBean() {}

    public ObjectUtilsBean(Long startNumber) {
        this.startNumber = startNumber;
        System.out.println(startNumber + "----1_Long--" + endNumber);
    }

    public ObjectUtilsBean(Long startNumber, Long endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        System.out.println(startNumber + "---2_Long---" + endNumber);
    }

    public ObjectUtilsBean(Integer startNumber, Integer endNumber) {
        this.startNumber = startNumber.longValue();
        this.endNumber = endNumber.longValue();
        System.out.println(startNumber + "---2_Integer---" + endNumber);
    }

    public ObjectUtilsBean(Long startNumber, Integer endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber.longValue();
        System.out.println(startNumber + "---1_Long--1_Integer---" + endNumber);
    }

    public Long getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Long startNumber) {
        this.startNumber = startNumber;
    }

    public Long getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Long endNumber) {
        this.endNumber = endNumber;
    }
}
