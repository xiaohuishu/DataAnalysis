package com.march.graduation.controller;

import com.google.common.util.concurrent.ListenableFuture;
import com.march.graduation.execute.FutureHelper;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
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

    private static final Sigar SIGAR = new Sigar();

    @RequestMapping("/monitorSystemState")
    public void monitor(HttpServletResponse response) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error("thread sleep failure: {}", e);
        }

        //long[] memInfo = SystemPropertiesTool.processMemoryInfo();
        PrintWriter printWriter = null;
        ListenableFuture isSuccess = FutureHelper.futureThreadLocal.get();

        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            logger.error("receive print writer failure: {}", e);
        }
        boolean isExitSigar = false;
        try {
            Mem mem = SIGAR.getMem();
            CpuPerc cpuPerc = SIGAR.getCpuPerc();
            if (mem != null && cpuPerc != null) {
                if (printWriter != null) {
                    printWriter.println(String.format("%.2f", cpuPerc.getCombined()) + "\t");
                    printWriter.println(mem.getTotal() + "\t");
                    printWriter.println(mem.getFree() + "\t");
                }
            }
        } catch (SigarException e) {
            isExitSigar = true;
            logger.error("sigar getMem failure: {}", e);
        }

        if (isExitSigar) {
            logger.error("read system memory info failure");
            if (printWriter != null) {
                printWriter.println(0 + "\t");
                printWriter.println(0 + "\t");
                printWriter.println(0 + "\t");
            }
        }

        if (isSuccess != null && printWriter != null) {
            printWriter.println(isSuccess.isDone() + "\t");
            FutureHelper.futureThreadLocal.remove();
        }
        if (printWriter != null) {
            printWriter.flush();
            printWriter.close();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //logger.info("test: " + System.getProperty("java.library.path"));
    }
}
