package com.march.graduation.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-29 时间: 下午2:46
//********************************************
public enum SalaryType {

    SALARY_LOW_5K("5k以下", 0, 5),
    SALARY_5k_10K("5k-10k", 5, 10),
    SALARY_10K_16K("10k-16k", 10, 16),
    SALARY_16K_21K("16k-21k", 16, 21),
    SALARY_21K_26K("21k-26k", 21, 26),
    SALARY_26K_INC("26k以上", 26, 0);

    SalaryType(String desc, int minSalary, int maxSalary) {
        this.desc = desc;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    private String desc;
    private int minSalary;
    private int maxSalary;

    public String getDesc() {
        return desc;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public static String caculSalarySize(Collection<String> salayList) {
        int salaryLow5k = 0;
        int salaryLow5k10k = 0;
        int salaryLow10k16k = 0;
        int salaryLow16k21k = 0;
        int salaryLow21k26k = 0;
        int salaryLow26kinc = 0;

        if(CollectionUtils.isNotEmpty(salayList)) {
            for (String salary : salayList) {
                String[] salaryRange = StringUtils.split(salary.replace("k", ""), "-");
                if (salaryRange.length == 1) {

                    if (salaryRange[0].endsWith("以下")) {
                        int intSalary = NumberUtils.toInt(salaryRange[0].substring(0, salaryRange[0].indexOf("以下")));
                        if (intSalary <= 5) {
                            salaryLow5k += 1;
                        }
                        if (intSalary > 5 && intSalary <= 10) {
                            salaryLow5k10k += 1;
                        }
                        if (intSalary > 10 && intSalary <= 16) {
                            salaryLow10k16k += 1;
                        }
                        if (intSalary > 16 && intSalary <= 21) {
                            salaryLow16k21k += 1;
                        }
                        if (intSalary > 21 && intSalary <= 26) {
                            salaryLow21k26k += 1;
                        }
                        if (intSalary > 26) {
                            salaryLow26kinc += 1;
                        }
                    }

                    if (salaryRange[0].endsWith("以上")) {
                        int intSalary = NumberUtils.toInt(salaryRange[0].substring(0, salaryRange[0].indexOf("以上")));
                        if (intSalary >= 26) {
                            salaryLow26kinc += 1;
                        }
                        if (intSalary >= 21 && intSalary < 26) {
                            salaryLow21k26k += 1;
                        }
                        if (intSalary >= 16 && intSalary < 21) {
                            salaryLow16k21k += 1;
                        }
                        if (intSalary >= 10 && intSalary < 16) {
                            salaryLow10k16k += 1;
                        }
                        if (intSalary >= 5 && intSalary < 10) {
                            salaryLow5k10k += 1;
                        }
                        if (intSalary < 5) {
                            salaryLow5k += 1;
                        }
                    }
                } else {

                    int minSalary = NumberUtils.toInt(salaryRange[0]);
                    int maxSalary = NumberUtils.toInt(salaryRange[1]);
                    if (maxSalary <= 5) {
                        salaryLow5k += 1;
                    }
                    if (minSalary >= 26) {
                        salaryLow26kinc += 1;
                    }
                    if (minSalary >= 5 && maxSalary <= 10) {
                        salaryLow5k10k += 1;
                    }
                    if (minSalary >= 10 && maxSalary <= 16) {
                        salaryLow10k16k += 1;
                    }
                    if (minSalary >= 16 && maxSalary <= 21) {
                        salaryLow16k21k += 1;
                    }
                    if (minSalary >= 21 && maxSalary <= 26) {
                        salaryLow21k26k += 1;
                    }
                }
            }
        }

        return String.format("%d,%d,%d,%d,%d,%d", salaryLow5k, salaryLow5k10k, salaryLow10k16k, salaryLow16k21k, salaryLow21k26k,
                salaryLow26kinc);
    }

}