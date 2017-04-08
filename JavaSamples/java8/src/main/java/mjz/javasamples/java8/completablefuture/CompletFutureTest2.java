package mjz.javasamples.java8.completablefuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

public class CompletFutureTest2 {

	private static Random rand = new Random();
	private static long t = System.currentTimeMillis();

	static int getMoreData() {
		System.out.println("begin to start compute");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
		return rand.nextInt(1000);
	}

	public static void main(String[] args) throws Exception {
		t5();
	}

	private static void t1() throws InterruptedException, ExecutionException, IOException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletFutureTest2::getMoreData);
		Future<Integer> f = future.whenComplete((v, e) -> {
			System.out.println(v);
			System.out.println(e);
		});
		System.out.println(f.get());
		// System.in.read();
	}

	private static void t2() throws InterruptedException, ExecutionException, IOException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletFutureTest2::getMoreData);
		Future<String> f = future.thenApply(i -> {
			return "result: " + i;
		});
		System.out.println(f.get());
		// System.in.read();
	}

	private static void t3() throws InterruptedException, ExecutionException, IOException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			return 100;
		});
		CompletableFuture<String> f = future.thenCompose(i -> {
			return CompletableFuture.supplyAsync(() -> {
				return (i * 10) + "";
			});
		});
		System.out.println(f.get()); // 1000
	}

	private static void t4() throws InterruptedException, ExecutionException, IOException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			return 100;
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			return "abc";
		});
		CompletableFuture<String> f = future.thenCombine(future2, (x, y) -> y + "-" + x);
		System.out.println(f.get()); // abc-100

	}

	private static void t5() throws InterruptedException, ExecutionException, IOException {
		Random rand = new Random();
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1 + rand.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 100;
		});
		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10000 + rand.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 200;
		});
		CompletableFuture<String> f = future.applyToEither(future2, i -> i.toString());
		System.out.println(f.get());
	}

	@Test
	public void t6() throws Exception {
		Random rand = new Random();
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
		    try {
		        Thread.sleep(1000 + rand.nextInt(1000));
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    System.out.println("---int");
		    return 100;
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
		    try {
		        Thread.sleep(1000 + rand.nextInt(1000));
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    System.out.println("---string");
		    return "abc";
		});
		CompletableFuture<Void> f =  CompletableFuture.allOf(future1,future2);
		f.join();
		//CompletableFuture<Object> f =  CompletableFuture.anyOf(future1,future2);
		//System.out.println(f.get());
	}
}
