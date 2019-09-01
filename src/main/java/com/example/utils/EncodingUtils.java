package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断字符串是否存在乱码
 * 0824:经过测试，这个方法不是很准确。
 * @author ben
 * 2019年8月24日
 */
public class EncodingUtils {

    public static void main(String[] args) {
	String str = "sdfsdf数￥……&@！鍙风瓑锛屽乏";
	String str2 = "Ã©Å¸Â©Ã©Â¡ÂºÃ¥Â¹Â³";
	boolean flag = isMessyCode(str);
	checkChinese(str2);
	System.out.println(flag);
    }

    private static boolean isChinese(char c) {
	Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
	    return true;
	}
	return false;
    }

    /**
     * 判断字符串是否为乱码
     * 
     * @param strName
     *            传入需要判断的字符串
     * @return 如果为乱码则返回true，否则返回false
     */
    public static boolean isMessyCode(String strName) {
	Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
	Matcher m = p.matcher(strName);
	String after = m.replaceAll("");
	String temp = after.replaceAll("\\p{P}", "");
	char[] ch = temp.trim().toCharArray();
	float chLength = 0;
	float count = 0;
	for (int i = 0; i < ch.length; i++) {
	    char c = ch[i];
	    if (!Character.isLetterOrDigit(c)) {
		if (!isChinese(c)) {
		    count = count + 1;
		}
		chLength++;
	    }
	}
	float result = count / chLength;
	if (result > 0.2) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * 判断字符串是否为乱码并返回
     * 
     * @param msg
     *            传入乱码字符串
     * @return 返回转换后的汉字数据
     */
    public static String toChinese(String msg) {
	if (isMessyCode(msg)) {
	    try {
		return new String(msg.getBytes("ISO8859-1"), "UTF-8");
	    } catch (Exception e) {
	    }
	}
	return msg;
    }

    public static void checkChinese(String str) {
	int chinaCount = 0;
	Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
	int length = 0;
	if (str != null) {
	    Matcher aMatcher = pattern.matcher(str);
	    System.out.println("是否有中文：" + (aMatcher.find() ? "有" : "无"));
	    char c[] = str.toCharArray();
	    length = c.length;
	    for (int i = 0; i < length; i++) {
		Matcher matcher = pattern.matcher(String.valueOf(c[i]));
		if (matcher.matches()) {
		    chinaCount++;
		}
	    }
	}
	System.out.println("字符串总个数：" + length);
	System.out.println("其中中文个数：" + chinaCount);
	System.out.println("非中文个数：" + (length - chinaCount));

    }
}
