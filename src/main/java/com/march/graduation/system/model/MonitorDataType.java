package com.march.graduation.system.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-19 时间: 下午3:40
//********************************************
public enum MonitorDataType {

    MEMORY_DATA_FILE("/proc/meminfo"),

    CPU_DATA_FILE("/proc/stat"),

    CPU_TEMPERATURE_DATA_FILE("/proc/acpi/thermal_zone/THM/temperature");

    MonitorDataType(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    public BufferedReader processFileReturnReader() {
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader( new InputStreamReader(
                    new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            logger.error("read [/proc/meminfo] failure: {}", e);
        }
        return br;
    }

    private static final Logger logger = LoggerFactory.getLogger(MonitorDataType.class);
}
