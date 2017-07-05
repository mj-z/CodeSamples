package mjz.javasamples.formatprint;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import mjz.javasamples.formatprint.po.TestObject;

/**
 * Unit test for simple App.
 */
public class BeanUtilTest {

	TestObject obj = new TestObject(); {
		obj.setDesc("test---");
	}
	
	@Test
	public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println(BeanUtils.getProperty(obj, "desc"));
		System.out.println(BeanUtils.getSimpleProperty(obj, "desc"));
	}
}
