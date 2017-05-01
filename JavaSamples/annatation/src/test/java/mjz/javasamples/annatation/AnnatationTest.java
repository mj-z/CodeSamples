package mjz.javasamples.annatation;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AnnatationTest {
	
	@Test
	public void testService() throws Exception {
		Service service = new Service();
		
		service.serve();
	}
}
