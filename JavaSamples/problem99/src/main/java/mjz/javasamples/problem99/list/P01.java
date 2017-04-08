package mjz.javasamples.problem99.list;

import java.util.List;

public class P01 {

	public static <T> T last(List<T> list) {
		if(list == null)
			throw new NullPointerException("list is null");
		
		return list.get(list.size() - 1);
	}
	
	public static <T> T secondLast(List<T> list) {
		if(list == null)
			throw new NullPointerException("list is null");
		
		int numOfElements = list.size();
		int pos = numOfElements - 2;
		
		return list.get(pos < 0 ? 0 : pos);
	}
	
	public static <T> T kth(List<T> list, int num) {
		return null;
	}
 }
