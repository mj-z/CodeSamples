package mjz.javasamples.multithread.generic;

import java.util.Optional;

import org.junit.Test;

public class TestOption {

	@Test
	public void testOptionMap() {
		Optional<Integer> opt1 = Optional.of(123);
		
		Optional<String> opt2 = opt1.map(o -> {
			return "321";
		});
		
		System.out.println(opt2.get());
	}
	
	@Test
	public void testOptionOrElseGet(){
		Optional<Integer> opt1 = Optional.of(123);
		
		Integer ret = opt1.orElseGet(() ->{
			Integer r = 10 * 2;
			return r;
		});
		
		System.out.println(ret);
	}
}
