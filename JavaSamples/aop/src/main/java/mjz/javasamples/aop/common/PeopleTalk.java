package mjz.javasamples.aop.common;

public class PeopleTalk implements ITalk {

	@Override
	public void talk(String msg) {
		System.out.println("talking something about " + msg);
	}

}
