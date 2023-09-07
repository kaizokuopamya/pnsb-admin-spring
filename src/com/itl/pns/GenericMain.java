package com.itl.pns;

public class GenericMain {

	public static void main(String[] args) {
		int res = 1 + 2;

		System.out.println(res);

		res = res - 1;
		System.out.println(res);
		res = res + 8;
		res = res % 7;
		System.out.println(res);

	}

	static void m1(Object i1) {
		System.out.println("object i1");
	}

	static void m1(String i1) {
		System.out.println("String i1");
	}

}
