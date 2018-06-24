package com.test.email.testEmail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestLinkedHashMap {

	public static void main(String args[]) {
		System.out.println("*************************LinkedHashMap*************");
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(6, "apple");
		map.put(2, "pear");
		map.put(3, "banana");

		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			System.out.println(key + "=" + map.get(key));
		}

		System.out.println("*************************HashMap*************");
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("6", "apple");
		map1.put("2", "pear");
		map1.put("3", "banana");

		for (Iterator it = map1.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			System.out.println(key + "=" + map1.get(key));
		}
	}
}
