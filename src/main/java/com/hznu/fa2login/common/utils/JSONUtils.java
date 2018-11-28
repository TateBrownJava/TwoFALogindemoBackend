package com.hznu.fa2login.common.utils;

public final class JSONUtils {

    /**
     * 得到格式化json数据  退格用\t 换行用\r
     *
     * @author yy's garden from 博客园
     * @since 2017-10-18 18:03:12
     */
    public static String printFormat(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    /**
     * 按照层级输出制表符
     *
     * @param level 层级
     * @return 最终的制表符
     * @author yy's garden from 博客园
     * @since 2017-10-18 18:03:12
     */
    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            // 每层输出一个制表符
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
