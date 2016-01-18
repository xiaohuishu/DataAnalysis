package com.march.graduation.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class URLBuilder {

    private static String TRAVEL_BASE = "http://travel.qunar.com";

    //private static String PLACE_BASE = TRAVEL_BASE + "/place";

    //直接转换的词汇
    private static Map<String, String> AMBIGUOUS_NAME_PINYIN_MAP = new HashMap<String, String>();

    private static List<String> AMBIGUOUS_NAMES = new ArrayList<String>();
    static {
        AMBIGUOUS_NAME_PINYIN_MAP.put("厦门", "xiamen");
        AMBIGUOUS_NAME_PINYIN_MAP.put("会稽", "kuaiji");
        AMBIGUOUS_NAME_PINYIN_MAP.put("重庆", "chongqing");
        AMBIGUOUS_NAME_PINYIN_MAP.put("番禺", "panyu");
        AMBIGUOUS_NAME_PINYIN_MAP.put("太行山", "taihangshan");
        AMBIGUOUS_NAME_PINYIN_MAP.put("长沙", "changsha");
        AMBIGUOUS_NAME_PINYIN_MAP.put("那曲", "naqu");
        AMBIGUOUS_NAME_PINYIN_MAP.put("喀什", "kashi");
        AMBIGUOUS_NAME_PINYIN_MAP.put("成都", "chengdu");
        AMBIGUOUS_NAME_PINYIN_MAP.put("长白山", "changbaishan");
        AMBIGUOUS_NAME_PINYIN_MAP.put("长岛", "changdao");
        AMBIGUOUS_NAMES.addAll(AMBIGUOUS_NAME_PINYIN_MAP.keySet());
    }

    public static void setTravelBase(String travelBase) {
        TRAVEL_BASE = travelBase;
        //PLACE_BASE = TRAVEL_BASE + "/place";
    }

    public static void setPlaceBase(String placeBase) {
        //PLACE_BASE = placeBase;
    }

    //////////////////////////////////////////////////////////////////////////////////
    public static String getPinyinIdentity(int id, String name) {
        return String.format("%s-%d", transformNameForUrlDisplay(name), id);
    }

    public static String getCountryURL(int id, String name) {
        return String.format(TRAVEL_BASE + "/p-gj%d-%s", id, transformNameForUrlDisplay(name));
    }

    public static String getProvinceURL(int id, String name) {
        return String.format(TRAVEL_BASE + "/p-sf%d-%s", id, transformNameForUrlDisplay(name));
    }

    public static String getCityURL(int id, String name) {
        return String.format(TRAVEL_BASE + "/p-cs%d-%s", id, transformNameForUrlDisplay(name));
    }

    public static String getPoiURL(int id, String name) {
        return String.format(TRAVEL_BASE + "/p-oi%d-%s", id, transformNameForUrlDisplay(name));
    }

    public static String getScenicURL(int id, String name) {
        return String.format(TRAVEL_BASE + "/p-jq%d-%s", id, transformNameForUrlDisplay(name));
    }

    /////////////////////////////////////////////////////////////////////////////
    /**
     * 将名字转换为拼音，规则参考代码
     * <p>
     * 如果name为空或者转换的结果为空，那么直接-
     * 
     * @param name
     * @return
     */
    public static String transformNameForUrlDisplay(String name) {
        if (name == null || name.length() == 0) return "_";
        name = name.trim();
        if (AMBIGUOUS_NAME_PINYIN_MAP.containsKey(name)) {
            return AMBIGUOUS_NAME_PINYIN_MAP.get(name);
        }

        if (name.endsWith(")") || name.endsWith("）")) {
            int idx = name.lastIndexOf('(');
            if (idx == -1) idx = name.lastIndexOf('（');
            if (idx > 0) name = name.substring(0, idx);
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < name.length();) {
            char ch = name.charAt(i);
            ch = Character.toLowerCase(ch);

            if (Character.isWhitespace(ch)) {
                if (!(sb.length() > 0 && sb.charAt(sb.length() - 1) == '_')) {
                    sb.append('_');
                }
            } else if (Character.isDigit(ch) || (ch >= 'a' && ch <= 'z') || ch == '_') {
                sb.append(ch);
            } else if (ch == '-') {
                sb.append("_");
            } else {
                //处理有歧义的字符串
                boolean found = false;
                for (String aname : AMBIGUOUS_NAMES) {
                    if (aname.charAt(0) == ch && //NL
                            ((i + 1) < name.length() && aname.charAt(1) == name.charAt(i + 1))) {
                        if (name.substring(i).startsWith(aname)) {
                            sb.append(AMBIGUOUS_NAME_PINYIN_MAP.get(aname));
                            i += aname.length();
                            found = true;
                            break;
                        }
                    }
                }
                if (found) continue;

                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch);
                if (arr != null && arr.length > 0) {
                    //多音字，取排序后的第一个拼音，其它語言要採用相同的字典，才能身處正確的拼音
                    String pinyin = arr[0];
                    if (Character.isDigit(pinyin.charAt(pinyin.length() - 1))) {
                        pinyin = pinyin.substring(0, pinyin.length() - 1);
                    }
                    sb.append(pinyin);
                }
            }
            i++;
            if (sb.length() > 20) break;
        }
        if (sb.length() == 0) {
            sb.append('_');
        }
        return sb.toString().replaceAll("-", "_");
    }

    public static void test() {
        System.out.println(transformNameForUrlDisplay("Musée Historique de Lausanne"));
        System.out.println(transformNameForUrlDisplay("厦门 d 太行山"));
        System.out.println(transformNameForUrlDisplay("北京（事实上)"));
        System.out.println(transformNameForUrlDisplay("西湖"));
        System.out.println(transformNameForUrlDisplay("会稽"));
        System.out.println(transformNameForUrlDisplay("会稽顶-额企鹅大而东方网企鹅改地方切问而近思   去我切问而近思郭德纲"));
        System.out.println(transformNameForUrlDisplay("Lake  gaga"));
        System.out.println(transformNameForUrlDisplay("Lake  gaga"));
        System.out.println(transformNameForUrlDisplay("Tianya See's"));
        System.out.println(transformNameForUrlDisplay("baidu~!@"));
        System.out.println(transformNameForUrlDisplay(""));
        System.out.println(getCityURL(299914, "北京"));
    }

    public static void main(String[] args) throws IOException {
        //        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        //        String line = null;
        //        while ((line = reader.readLine()) != null) {
        //            System.out.println(line + "\t" + transformNameForUrlDisplay(line));;
        //        }
        test();
    }
}
