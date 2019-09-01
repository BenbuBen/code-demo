package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.TreeMap;

public class StringTest {
	
	/**
	 * 任意给定英文字符串，求出各个字母出现的次数，并按照字母出现的次数从高到低排序。
	 * 思路：
	 * 1、先建立一个map,存储对应的字母以及次数 key-value,
	 * 2、自定义一个value的比较器Comparator,实现map按照key对应的value值排序。
	 * 3、新建一个TreeMap,并传入比较器自定义排序规则，将之前map复制到treemap中。
	 * 4、遍历treemap即为所求。
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		String[] str ="shdfuehfhrufhdheufhfsjfuqoweptumnbvfutiypeyasywhylmnzbvcajdiwu".split("");
		Map<String,Integer> map = new HashMap<String, Integer>();
		for(int i=0;i<str.length;i++){
			if(map.containsKey(str[i])){
				int count = map.get(str[i]);
				map.put(str[i],count+1);
			}else{
				map.put(str[i],1);
			}
		}
		MapValueComparator mapValueComparator = new MapValueComparator(map);
		Map<String,Integer> sortmap = new TreeMap<String, Integer>(mapValueComparator);
		sortmap.putAll(map);
		System.out.println(sortmap.toString());
		
	}

}

class MapValueComparator<T extends Comparable<T>> implements Comparator<String> {
    private Map<String, T> map = null;

    public MapValueComparator(Map<String, T> map) {
        this.map = map;
    }

    public int compare(String o1, String o2) {
        int r = map.get(o2).compareTo(map.get(o1));
        if (r == 0) {
        	r= 1;
        } 
        return r;
    }
}