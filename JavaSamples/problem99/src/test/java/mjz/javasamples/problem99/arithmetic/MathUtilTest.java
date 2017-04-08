package mjz.javasamples.problem99.arithmetic;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;


public class MathUtilTest {
	
	@Test
	public void testFloat_RtnWithUnexpectedPrecision() {
		System.out.println(0.1 + 0.2);
	}
	

	@Test
	public void testFloat_RtnWithGoodPrecision() {
		System.out.println(0.1 + 0.2);
	}

	@Test
	public void shouldSay7IsAPrimeNumber() throws Exception {
		boolean prime = MathUtil.isPrime(7);
		assertTrue(prime);
	}

	@Test
	public void shouldSay10IsNotAPrimeNumber() throws Exception {
		boolean prime = MathUtil.isPrime(10);
		assertFalse(prime);
	}
	
	@Test
	public void shouldFindPrimeFactorsOf315() throws Exception {
	    List<Integer> primeFactors = MathUtil.primeFactors(315);
	    assertThat(primeFactors, hasItems(3, 3, 5, 7));
	}

	@Test
	public void shouldFindPrimeFactorsOf33() throws Exception {
	    List<Integer> primeFactors = MathUtil.primeFactors(33);
	    assertThat(primeFactors, hasItems(3, 11));
	}

	@Test
	public void _8_isthesumof_3_and_5() throws Exception {
	    List<Integer> numbers = MathUtil.goldbach(8);
	    assertThat(numbers, hasSize(2));
	    assertThat(numbers, hasItems(3, 5));	    
	}

	@Test
	public void _28_isthesumof_5_and_23() throws Exception {
	    List<Integer> numbers = MathUtil.goldbach(28);
	    assertThat(numbers, hasSize(2));
	    assertThat(numbers, hasItems(5, 23));
	}
	
	@Test
	public void _28_isthesumof_5_and_23___() throws Exception {
	    List<Integer> numbers = MathUtil.goldbach(1105836890);
	    System.out.println(numbers);
	    //assertThat(numbers, hasSize(2));
	    //assertThat(numbers, hasItems(5, 23));
	}

}
