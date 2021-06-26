package com.vbrug.fw4j.common.util;

/**
 * @author vbrug/Documents
 * @since 1.0.0
 */
public class EncryptUtilsTest {

    public static void main(String[] args) throws Exception {
        EncryptUtils td = new EncryptUtils("oxyenergy");
        td.encrypt("/home/vbrug/Documents/sd_clear.qps", "/home/vbrug/Documents/sd_clear.qps_e"); //加密
        td.decrypt("/home/vbrug/Documents/sd_clear.qps_e", "/home/vbrug/Documents/sd_clear.qps_j"); //解密
    }

}
