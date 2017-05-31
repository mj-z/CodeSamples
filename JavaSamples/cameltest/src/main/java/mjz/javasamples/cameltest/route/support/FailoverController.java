package mjz.javasamples.cameltest.route.support;

import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("failoverController")
public class FailoverController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LockFile locker;
	
	@Autowired
	private CamelContext camelContext;
	
	private boolean prelock = false;
		
	public void check() throws Exception {
		
		Consumer consumer = camelContext.getRoute("testFailOver").getConsumer();
		boolean newlock;
		if(newlock = locker.lock()) {
			consumer.start();			
			
			if(newlock != prelock) {
				log.info("lock stat changed to {}", newlock);
				prelock = newlock;
			}
		} 
		
	}

}
