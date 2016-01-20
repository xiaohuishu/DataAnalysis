package com.march.graduation.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-20 时间: 下午3:38
//********************************************
public class HanyuConvertPinyinHelper {

    private static final Logger logger = LoggerFactory.getLogger(HanyuConvertPinyinHelper.class);

    private static final HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();

    static {
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    public static String[] convertByChar(char hz) {
        String[] Pinyin = null;

        try {
            Pinyin = PinyinHelper.toHanyuPinyinStringArray(hz, outputFormat);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("parse pinye failure : {}", e);
        }
        return Pinyin;
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String convertbyString(String chinese) {
        StringBuilder pybf = new StringBuilder();
        char[] arr = chinese.toCharArray();
        for (char anArr : arr) {
            if (anArr > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(anArr, outputFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(anArr);
            }
        }
        return pybf.toString();
    }

}
