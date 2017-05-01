package mjz.javasamples.aop.javaproxy;

import org.junit.Test;

import mjz.javasamples.aop.common.ITalk;
import mjz.javasamples.aop.common.PeopleTalk;

public class DynamicProxyTest {

	@Test
	public void testProxy() {
		ITalk peopleTalk = (ITalk)new DynamicProxy().bind(new PeopleTalk());
		
		peopleTalk.talk("something...");
	}
}
