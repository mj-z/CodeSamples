package mjz.javasamples.java8.calcmemobjsize;

import java.util.HashMap;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class CalcMemObjSize {

	public static void main(String[] args) {
		
		System.out.println(ObjectSizeCalculator.getObjectSize(new HashMap<String, Integer>(100000)));
		System.out.println(ObjectSizeCalculator.getObjectSize(3));
		System.out.println(ObjectSizeCalculator.getObjectSize(3L));
		System.out.println(ObjectSizeCalculator.getObjectSize(new int[]{1, 2, 3, 4, 5, 6, 7 }));
		System.out.println(ObjectSizeCalculator.getObjectSize(new int[100]));
		
	}
}
