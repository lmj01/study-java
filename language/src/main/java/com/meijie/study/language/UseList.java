package com.meijie.study.language;

import java.util.ArrayList;
import java.util.Collection;
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
	protected int sortList(List<Integer> list) {
		int count = 1;
		List<Integer> pos = list.stream().filter(x->x>=0).collect(Collectors.toList());
		List<Integer> neg = list.stream().filter(x->x<0).collect(Collectors.toList());
		pos.sort((a,b)->a-b);
		neg.sort((a,b)->b-a);
		System.out.println("\n--------------------------");
		for (Integer a : pos) System.out.print(a + ", ");
		System.out.println();
		for (Integer a : neg) System.out.print(a + ", ");
		//System.out.println();
//		Collections.sort(list, new Comparator<Integer>() {
//			public int compare(Integer a, Integer b) {
//				// 0 -- =
//				// 1 -- > 
//				// -1 -- <
//				if (a < 0 && b > 0) return 1;
//				if (b < 0 && a > 0) return -1;
//				if (a < b) return -1;
//				return 1;
//			}
//		});
		List<Integer> res = new ArrayList<>();
		res.addAll(pos);
		res.addAll(neg);
		System.out.println();
		for (Integer a : res) System.out.print(a + ", ");
		if (res.size() > 2) {
			for (int i=2; i < res.size(); i+=2) {
				if (res.get(i) - res.get(i-1) == 1) continue;
				else count++;
			}
		}
		return count;
	}
	public void demo2() {
		System.out.print("\nsequence but much stage order situation");
		List<Aggregation> data1 = new ArrayList<>();
		data1.add(new Aggregation(22, 31));
		data1.add(new Aggregation(32, -1));
		data1.add(new Aggregation(0, 21));
		
		List<Integer> list1 = data1.stream()
				.map(x -> x.getStartId())
				.collect(Collectors.toList());
		list1.addAll(data1.stream().map(x->x.getEndId()).collect(Collectors.toList()));
		
		//startList.sort((a,b)->a-b);
		
		int count = sortList(list1);
		System.out.println(String.format("\nvalid count is %d", count));
		for (Integer a : list1) {
			System.out.print(a+", ");
		}
		
		System.out.print("\n\norder sort situation");
		List<Aggregation> data2 = new ArrayList<>();
		data2.add(new Aggregation(0, 10));
		data2.add(new Aggregation(11, -1));
		
		List<Integer> list2 = data2.stream()
				.map(x->x.getStartId())
				.collect(Collectors.toList());
		list2.addAll(data2.stream().map(x->x.getEndId()).collect(Collectors.toList()));
		count = sortList(list2);
		System.out.println(String.format("\nvalid count is %d", count));
		for (Integer a : list2) {
			System.out.print(a+", ");
		}
		
		List<Aggregation> data3 = new ArrayList<>();
		data3.add(new Aggregation(11, -1));
		data3.add(new Aggregation(0, 10));
		
		List<Integer> list3 = data3.stream()
				.map(x->x.getStartId())
				.collect(Collectors.toList());
		list3.addAll(data3.stream().map(x->x.getEndId()).collect(Collectors.toList()));
		count = sortList(list3);
		System.out.println(String.format("\nvalid count is %d", count));
		for (Integer a : list3) {
			System.out.print(a+", ");
		}
		
		System.out.print("\n\nrepeat situation");
		List<Aggregation> data4 = new ArrayList<>();
		data4.add(new Aggregation(0, -1));
		data4.add(new Aggregation(0, -1));
		
		List<Integer> list4 = data4.stream()
				.map(x->x.getStartId())
				.collect(Collectors.toList());
		list4.addAll(data4.stream().map(x->x.getEndId()).collect(Collectors.toList()));
		count = sortList(list4);
		System.out.println(String.format("\nvalid count is %d", count));
		for (Integer a : list4) {
			System.out.print(a+", ");
		}
		System.out.println();
	}
	
	public static void test() {
		
		UseList ul = new UseList();
		
//		ul.demo1();

		ul.demo2();
	}
}
