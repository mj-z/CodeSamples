package mjz.javasamples.objectpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.apache.commons.pool2.impl.GenericObjectPool;

public class Main {
	
	GenericObjectPool<StringBuffer> pool = new GenericObjectPool<StringBuffer>(new StringBufferFactory());
	
	{		
		pool.setMaxTotal(8);
		pool.setMaxIdle(2);		
	}

	public static void main(String[] args) throws Exception {
				
		Main m = new Main();
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		IntStream.rangeClosed(0, 20).forEach(i->{
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					StringBuffer sb = null;
					try {
						sb = m.getObject();
						
						Thread.sleep(3000);
						
					} catch (Exception e) {
						throw new RuntimeException(e);
					} finally {
						
						if(null != sb)
							m.releaseObject(sb);
					}
				}
			});		
		});	
		
		System.out.println("---------------------done");
		
		executor.shutdown();
	}
	
	StringBuffer getObject() {		
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	void releaseObject(StringBuffer sb) {
		pool.returnObject(sb);
	}
}
