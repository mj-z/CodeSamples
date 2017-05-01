package mjz.javasamples.aop.cglibway;

import org.junit.Test;

import mjz.javasamples.aop.common.ITalk;
import mjz.javasamples.aop.common.PeopleTalk;

public class CglibProxyTest {
	@Test
	public void testProxy() {
		CglibProxy proxy = new CglibProxy();
		ITalk peopleTalk = (ITalk) proxy.getProxy(PeopleTalk.class);

		peopleTalk.talk("something...");
	}
}
