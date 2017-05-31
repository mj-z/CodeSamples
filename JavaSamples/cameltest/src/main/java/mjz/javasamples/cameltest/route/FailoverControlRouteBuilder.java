package mjz.javasamples.cameltest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import mjz.javasamples.cameltest.route.support.LockFile;

public class FailoverControlRouteBuilder extends RouteBuilder {
		
	
	
	@Autowired
	private LockFile lockFile;

	@Override
	public void configure() throws Exception {
		 
		 from("timer://failOverController?fixedRate=true&period=5000")
		 .bean("failoverController", "check");
		 
		 
		
	}

}
