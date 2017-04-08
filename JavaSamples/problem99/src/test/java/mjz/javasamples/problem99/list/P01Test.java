package mjz.javasamples.problem99.list;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class P01Test {
	
	@Test
	public void shouldFindLastElementFromAListOfAlphabets() throws Exception {
		assertThat(P01.last(Arrays.asList("a", "b", "c", "d")), is(equalTo("d")));
	}
	
	@Test
	public void shouldFindSecondLastElementFromAList() throws Exception {
	    List<Integer> numbers = asList(1, 2, 11, 4, 5, 8, 10, 6);
	    assertThat(P01.secondLast(numbers), is(equalTo(10)));
	}
	
	@Test
	public void shouldFindKthElementFromAList() throws Exception {
	    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
	    assertThat(P01.kth(numbers, 2), is(equalTo(3)));
	}
}
