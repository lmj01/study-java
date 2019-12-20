package com.meijie.study.language;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UseMap {
	private Map<Integer, String> notes;

	public Map<Integer, String> getNotes() {
		return notes;
	}

	public void setNotes(Map<Integer, String> notes) {
		this.notes = notes;
	}
	
	public static class CountValue {
		private int facial;
		private int lingual;
		
		public CountValue(int facial, int lingual) {
			this.facial = facial;
			this.lingual = lingual;
		}
		
		public int getFacial() {
			return facial;
		}
		public void setFacial(int facial) {
			this.facial = facial;
		}
		public int getLingual() {
			return lingual;
		}
		public void setLingual(int lingual) {
			this.lingual = lingual;
		}		
	}
	
	public static void test() {
		UseMap map = new UseMap();
		map.setNotes(new HashMap<>());
		Map<Integer, String> notes = map.getNotes();
		notes.put(1, "val 1");
		notes.put(20, "val 20");
		notes.put(15, "val 15");
		notes.put(2, "val 2");
		int i = 0;
		System.out.println("-------------");
		notes.forEach((key,val)->{			
			System.out.println(String.format("key is %s, value is %s", key, val));
		});
		
		// 排序
		Map<Integer, String> sorted = new LinkedHashMap<>();
		notes.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByKey())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		
		System.out.println("-------------");
		for (Integer key : sorted.keySet()) {
			i+=2;
			System.out.println(String.format("key is %s, value is %s, %d", key, notes.get(key), i));
		}
		
		System.out.println("-------------");
		
		Map<Integer,CountValue> crowCount = new HashMap<Integer, CountValue>();
		
		crowCount.put(4, new CountValue(0,0));
		crowCount.put(5, new CountValue(0,0));
		CountValue cv1 = crowCount.get(5);
		cv1.setFacial(cv1.getFacial()+1);
		crowCount.forEach((k,v)->{
			System.out.println(String.format("key is %s, value is (%d, %d)", k, v.getFacial(), v.getLingual()));
		});
		CountValue cv2 = crowCount.get(3);
		if (cv2 != null) System.out.println(String.format("key is 3, default value is %s", cv2.getFacial(), cv2.getLingual()));
	}
	

}
