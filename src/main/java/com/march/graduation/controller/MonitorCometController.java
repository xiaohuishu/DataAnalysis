package com.march.graduation.controller;

import com.google.common.util.concurrent.ListenableFuture;
import com.march.graduation.execute.FutureHelper;
import com.march.graduation.system.SystemPropertiesTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午7:06
// ********************************************
@Controller
public class MonitorCometController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MonitorCometController.class);

    @RequestMapping("/monitorSystemState")
    public void monitor(HttpServletResponse response) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error("thread sleep failure: {}", e);
        }

        long [] memInfo = SystemPropertiesTool.processMemoryInfo();
        if(memInfo == null || memInfo.length <= 0) {
            logger.error("read system memory info failure");
        } else {
            ListenableFuture isSuccess = FutureHelper.futureThreadLocal.get();
            PrintWriter printWriter = null;
            try {
                double rate = SystemPropertiesTool.caculCpuRate();
                String cpuRate = String.format("%.2f", rate);
                printWriter = response.getWriter();
                printWriter.println(cpuRate + "\t");
                printWriter.println(memInfo[0] + "\t");
                printWriter.println(memInfo[1] + "\t");
                if(isSuccess != null) {
                    printWriter.println(isSuccess.isDone() + "\t");
                    FutureHelper.futureThreadLocal.remove();
                }

            } catch (IOException e) {
                logger.error("response getWriter exception: {} ", e);
            } finally {
                if(printWriter != null) {
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
