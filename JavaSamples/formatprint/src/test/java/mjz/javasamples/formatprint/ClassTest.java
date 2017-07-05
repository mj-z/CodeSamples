package mjz.javasamples.formatprint;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

public class ClassTest {

	@Test
	public void test() {
		System.out.println(ClassUtils.isPrimitiveOrWrapper(int.class));
		System.out.println(Number.class.isAssignableFrom(int.class));
		System.out.println(Number.class.isAssignableFrom(Integer.class));
		System.out.println(Integer.class.isAssignableFrom(int.class));
	}
}
