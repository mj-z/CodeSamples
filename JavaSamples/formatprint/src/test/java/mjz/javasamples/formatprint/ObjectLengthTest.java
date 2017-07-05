package mjz.javasamples.formatprint;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Test;

public class ObjectLengthTest {

	@Test
	public void testString() {
		System.out.println("testst".length());
	}
	
	@Test
	public void testDouble() {
		double d= 0.4d;
		String s = "" + d;
		System.out.println(s.length());
	}
	
	@Test
	public void testPrint() {
		System.out.println(String.format("%s", "this is a test!"));
		System.out.println(String.format("%3.6s", "this is a test!"));
		System.out.println(String.format("%.6s", "this is a test!"));
		System.out.println(String.format("%3.6s", "1"));
		System.out.println(String.format("%6.6s", "1"));
		System.out.println(String.format("%.6s...", "1"));
		System.out.println(String.format("|%.5s|", "Hello World"));
		System.out.println(String.format("%s", new Date()));
		System.out.println(String.format("%s", LocalDateTime.now()));
		System.out.println(String.format("%.4f", (double)1/3));
		System.out.println(String.format("%.4f", (double)2/3));
		System.out.println(String.format("%.4f", 0.4));
		System.out.println(String.format("|%020d|", 93));
		System.out.println(String.format("%%%d.%ds", 6, 6));		
		System.out.println(String.format(String.format("%%%d.%ds", 6, 6), "1124253234234"));
		System.out.println(String.format("%d", (short)6));	
		System.out.println(String.format("%%.%df", 2));
	}
}
