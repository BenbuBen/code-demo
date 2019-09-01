package com.example.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * java正则表达式demo
 * @author ben
 * 2019年8月10日
 */
public class RegDemo {

    public static void main(String[] args) {
	
    }
    
    /**
     * 匹配中文字符，根据ASCII
     */
    @Test
    public void test1(){
	String str = "[^\u4e00-\u9fa5]";
	Pattern pattern = Pattern.compile(str);
	Matcher m = pattern.matcher("！·*……“”");
	System.out.println(m.find());
    }
    
    /**
     * 匹配所有字符
     */
    public void test2(){
	String str = "(\\s\\S)*";
	Pattern pattern = Pattern.compile(str);
	Matcher m = pattern.matcher("！·*……“”");
	System.out.println(m.find());
    }
}
