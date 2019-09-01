package com.example.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * java正则表达式demo
 * 
 * @author ben 2019年8月10日
 */
public class RegDemo {

    public static void main(String[] args) {

    }

    /**
     * 匹配中文字符，根据ASCII
     */
    @Test
    public void test1() {
	String str = "[^\u4e00-\u9fa5]";
	Pattern pattern = Pattern.compile(str);
	Matcher m = pattern.matcher("！·*……“”");
	System.out.println(m.find());
    }

    /**
     * 匹配所有字符
     */
    public void test2() {
	String str = "(\\s\\S)*";
	Pattern pattern = Pattern.compile(str);
	Matcher m = pattern.matcher("！·*……“”");
	System.out.println(m.find());
    }

    /**
     * 实现类似notepad++全词匹配
     */
    @Test
    public void wordMatch() {
	// 按指定模式在字符串查找
	String line = "This order wasdt place dtee for QT3000! OK? " + ",DT= (Dt where T1.Dt= ";
	String pattern = "[^a-zA-Z](?i)DT[^a-zA-Z]";
	StringBuffer sb = new StringBuffer();
	// 创建 Pattern 对象
	Pattern r = Pattern.compile(pattern);
	Pattern rep = Pattern.compile("DT");
	// 现在创建 matcher 对象
	Matcher m = r.matcher(line);
	String newlines = line;
	while (m.find()) {
	    String cnt = m.group();
	    //System.out.println(cnt + " --- ");
	    String replacecnt = cnt.toUpperCase().replaceAll("DT", "PT_DT");
	    //System.out.println(cnt + " --- " + replacecnt);
	    if (cnt.contains("(")) {
		newlines = newlines.replaceAll("\\" + cnt, replacecnt);
	    } else {
		newlines = newlines.replaceAll(cnt, replacecnt);
	    }
	}
	sb.append(newlines);
	System.out.println(sb.toString());
    }
}
