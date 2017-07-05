package mjz.javasamples.java8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class AppTest  {
	
	private Logger log1 = LoggerFactory.getLogger(getClass());
	
	private static Logger log2 = LoggerFactory.getLogger(AppTest.class);

	@Test
	public void test() {
		System.out.println(log1);
		System.out.println(log2);
		System.out.println(log2 == log1);
		
		Logger log3 = LoggerFactory.getLogger(getClass());
		System.out.println(log3 == log1);
	}
}
