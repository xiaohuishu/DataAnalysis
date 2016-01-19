package com.march.graduation.system;

import com.march.graduation.system.model.MonitorDataType;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-19 时间: 下午3:25
//********************************************
public class SystemPropertiesTool {

    private static final Logger logger = LoggerFactory.getLogger(SystemPropertiesTool.class);

    public static long [] processMemoryInfo() {

        BufferedReader br = MonitorDataType.MEMORY_DATA_FILE.processFileReturnReader();

        long [] result = new  long [4];
        String str;
        StringTokenizer token;
        try {
            while ((str = br.readLine()) != null )
            {
                token = new StringTokenizer(str);
                if (!token.hasMoreTokens()) {
                    continue;
                }
                str = token.nextToken();
                if (!token.hasMoreTokens()) {
                    continue;
                }
                if (str.equalsIgnoreCase( "MemTotal:" ))
                    result[0 ] = NumberUtils.toLong(token.nextToken());
                else  if (str.equalsIgnoreCase( "MemFree:" ))
                    result[1 ] = NumberUtils.toLong(token.nextToken());
                else  if (str.equalsIgnoreCase( "SwapTotal:" ))
                    result[2 ] = NumberUtils.toLong(token.nextToken());
                else  if (str.equalsIgnoreCase( "SwapFree:" ))
                    result[3 ] = NumberUtils.toLong(token.nextToken());
            }
        } catch (IOException e) {
            logger.error("getMemInfo exec failure: {}", e);
        }

        return result;
    }

    public static double caculCpuRate() {
        BufferedReader br = MonitorDataType.CPU_DATA_FILE.processFileReturnReader();

        if(br == null) {
            return 0;
        }
        StringTokenizer token;
        try {
            token = new StringTokenizer(br.readLine());
            token.nextToken();
            int user1 = Integer.parseInt(token.nextToken());
            int nice1 = Integer.parseInt(token.nextToken());
            int sys1 = Integer.parseInt(token.nextToken());
            int idle1 = Integer.parseInt(token.nextToken());

            try {
                Thread.sleep(1000 );
            } catch (InterruptedException e) {
                logger.error("thread sleep failure: {}", e);
            }

            br = MonitorDataType.CPU_DATA_FILE.processFileReturnReader();

            token = new StringTokenizer(br.readLine());
            token.nextToken();
            int user2 = Integer.parseInt(token.nextToken());
            int nice2 = Integer.parseInt(token.nextToken());
            int sys2 = Integer.parseInt(token.nextToken());
            int idle2 = Integer.parseInt(token.nextToken());

            return ( double )((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) / ( double )((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));

        } catch (IOException e) {
            logger.error("getCpuInfo exec failure: {}", e);
        }
        return 0;
    }
}
