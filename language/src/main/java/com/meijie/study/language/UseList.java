package com.meijie.study.language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UseList {

	class BtnInfo implements Comparable<BtnInfo> {
		public int start;
		public int end;
		
		public BtnInfo(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		@Override
		public int compareTo(BtnInfo s) {
			if (this.start >= s.start) {
				return 1;
			}
			return -1;
		}
	}
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
		
		List<BtnInfo> data1 = new ArrayList<>();
		data1.add(new BtnInfo(22, 31));
		data1.add(new BtnInfo(32, -1));
		data1.add(new BtnInfo(0, 21));
		
		data1.sort(Comparator.naturalOrder()); // 正序
//		Collections.sort(data1); // 正序
		for (BtnInfo bi : data1) {
			System.out.println(String.format("s(%d)--e(%d)", bi.start, bi.end));
		}
		
	}
	
	
	public static void test() {
		
		UseList ul = new UseList();
		
//		ul.demo1();

		ul.demo2();
	}
	
}
