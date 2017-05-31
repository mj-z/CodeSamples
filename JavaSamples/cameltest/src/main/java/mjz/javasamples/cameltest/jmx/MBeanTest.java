package mjz.javasamples.cameltest.jmx;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service
@ManagedResource(objectName="bean:name=Test")
public class MBeanTest {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Produce(uri="direct:eventbus")
	private ProducerTemplate producer;
	
	@ManagedOperation
	public void test() {
		producer.sendBody("this is a test");
		log.info("managed to call method - test ");
	}
}
