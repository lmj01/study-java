package com.meijie.study.language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UseList {

	public static class Item {
		private int ia;
		private String str;
		
		public Item(int a, String str) {
			this.ia = a;
			this.str = str;
		}
		
		public int getIa() {
			return ia;
		}
		public void setIa(int ia) {
			this.ia = ia;
		}
		public String getStr() {
			return str;
		}
		public void setStr(String str) {
			this.str = str;
		}		
	}
	
	public void demo1() {
		List<Item> list1 = new ArrayList<>();
		list1.add(new Item(11, "aa"));
		list1.add(new Item(12, "bb"));
		list1.add(new Item(13, "cc"));
		
		List<Item> list2 = new ArrayList<>();
		list2.add(new Item(21, "2aa"));
		list2.add(new Item(22, "2bb"));
		list2.add(new Item(23, "2cc"));
		
		list1.addAll(list2);
		for (Item it : list1) {
			String str = String.format("%d--%s", it.getIa(), it.getStr());
			System.out.println(str);
		}
	}
	
	public static class Aggregation {
		private int startId;
		private int endId;
		public Aggregation(int start, int end) {
			this.startId = start;
			this.endId = end;
		}
		public int getStartId() {
			return startId;
		}
		public void setStartId(int startId) {
			this.startId = startId;
		}
		public int getEndId() {
			return endId;
		}
		public void setEndId(int endId) {
			this.endId = endId;
		}
		
	}
	public void demo2() {
		List<Aggregation> data1 = new ArrayList<>();
		data1.add(new Aggregation(22, 31));
		data1.add(new Aggregation(32, -1));
		data1.add(new Aggregation(0, 21));
		
		List<Integer> startList = data1.stream()
				.map(x -> x.getStartId())
				.collect(Collectors.toList());
		List<Integer> endList = data1.stream() 
				.map(x -> x.getEndId())
				.collect(Collectors.toList());
		startList.addAll(endList);
		
		//startList.sort((a,b)->a-b);
		
		Collections.sort(startList, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				// 0 -- =
				// 1 -- > 
				// -1 -- <
				if (a < 0 && b > 0) return 1;
				if (b < 0 && a > 0) return 1;
				if (a < b) return -1;
				return 1;
			}
		});
		int count = 1;
		if (startList.size() > 2) {
			for (int i=2; i<startList.size();i+=2) {
				if (startList.get(i) - startList.get(i-1) == 1) continue;
				else count++;
			}
		}
		System.out.println(String.format("valid count is %d", count));
		for (Integer a : startList) {
			System.out.println(a);
		}
	}
	
	public static void test() {
		
		UseList ul = new UseList();
		
//		ul.demo1();

		ul.demo2();
	}
}
