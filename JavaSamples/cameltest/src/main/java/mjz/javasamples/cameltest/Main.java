package mjz.javasamples.cameltest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("basic.xml");
		
		while(true) {
			Thread.sleep(1000);
		}
	}
}
