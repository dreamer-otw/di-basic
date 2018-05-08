package com.dreamer.basic.backstage.sys.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LogPtint {

    @Value("${h5.log.print}")
    boolean logPrint;

    public void logPrintController(String str) {
        if (logPrint) {
            System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+">>>>>:");
            System.out.println(str);
        }
    }
}
