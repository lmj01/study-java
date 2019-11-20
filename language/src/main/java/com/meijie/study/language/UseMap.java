package com.meijie.study.language;

import java.util.HashMap;
import java.util.Map;

public class UseMap {
	private Map<Integer, String> notes;

	public Map<Integer, String> getNotes() {
		return notes;
	}

	public void setNotes(Map<Integer, String> notes) {
		this.notes = notes;
	}
	
	public static void test() {
		UseMap map = new UseMap();
		map.setNotes(new HashMap<>());
		Map<Integer, String> notes = map.getNotes();
		notes.put(1, "val 1");
		notes.put(2, "val 2");
		int i = 0;
		System.out.println("-------------");
		notes.forEach((key,val)->{			
			System.out.println(String.format("key is %s, value is %s", key, val));
		});
		System.out.println("-------------");
		for (Integer key : notes.keySet()) {
			i+=2;
			System.out.println(String.format("key is %s, value is %s, %d", key, notes.get(key), i));
		}
		
	}
}
