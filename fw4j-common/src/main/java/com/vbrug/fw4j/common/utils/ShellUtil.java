package com.vbrug.fw4j.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ShellUtil {


    public static Integer run(String... cmd) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println("开始执行脚本：输入参数-------------------\n" + Arrays.stream(cmd).collect(Collectors.joining("\n")));
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process ps = processBuilder.start();
        Integer result = null;
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
            result = ps.waitFor();
        } catch (InterruptedException e) {
            System.err.println("shell执行异常!!!");
            throw new RuntimeException(e.getMessage());
        } finally {
            if (input != null)
                input.close();
        }
        System.out.println("执行脚本结束耗时--" + (System.currentTimeMillis() - start) / 1000 + "s");
        return result;
    }

    public static void main(String[] args) throws IOException {
        Integer result = ShellUtil
                .run("/vdata/archives/documents/shell/sh_alg.sh",
                        "/vdata/archives/shyqfx/alg",
                        "data.tsv.2020041");
        System.out.println(result);
    }
}
