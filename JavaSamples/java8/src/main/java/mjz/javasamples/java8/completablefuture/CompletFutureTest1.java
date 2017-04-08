package mjz.javasamples.java8.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletFutureTest1 {
	public static void main(String[] args) {
		CompletableFuture<String> future = ask();
		Thread t1 = new TestThread(future), t2 = new TestThread(future);
		t1.start();
		t2.start();
		try {
			System.out.println("2 secs later, about to complete future with 42");
			TimeUnit.SECONDS.sleep(2);			
			future.complete("42");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CompletableFuture<String> ask() {
		final CompletableFuture<String> future = new CompletableFuture<>();
		// ...
		return future;
	}
}

class TestThread extends Thread {
	CompletableFuture<String> future = null;

	public TestThread(CompletableFuture<String> future) {
		this.future = future;
	}

	public void run() {
		System.out.println("start block get.");

		String str = null;
		try {
			str = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("exit block get and [" + str + "].");
	}
}
