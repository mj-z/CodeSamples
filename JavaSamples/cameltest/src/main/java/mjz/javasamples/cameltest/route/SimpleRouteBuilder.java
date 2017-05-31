package mjz.javasamples.cameltest.route;

import java.time.LocalTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		 from("direct:eventbus")
		 .id("mainRoute")
		 //.to("metrics:timer:simple.timer?action=start")
         .to("log:+++eventbus+++")
         //.to("metrics:timer:simple.timer?action=stop")
         ;
		 
		 from("timer://testFailOver?fixedRate=true&period=10000")
		 .id("testFailOver")
		 .autoStartup(false)
		 .process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				String msg = "this is a test --- " + LocalTime.now();
				exchange.getIn().setBody(msg);
			}
		 })
		 .to("log:+++testFailOver+++");
		 
	}

}
