package mjz.javasamples.easybatch;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import org.easybatch.core.filter.PoisonRecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.reader.BlockingQueueRecordReader;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.PoisonRecord;
import org.easybatch.core.record.Record;
import org.easybatch.core.writer.StandardOutputRecordWriter;

import com.google.common.collect.Lists;


/**
 * Hello world!
 *
 */
public class BatchMain {
	public static void main(String[] args) throws Exception {
		BlockingQueue<Record> workQueue = new LinkedBlockingQueue<>();
		
		Job job = new JobBuilder()
                .named("mytest")
                .reader(new BlockingQueueRecordReader(workQueue))
                .filter(new PoisonRecordFilter())
                .writer(new StandardOutputRecordWriter())
                .build();
		
		JobExecutor jobExecutor = new JobExecutor();
		Future<JobReport> reportFuture = jobExecutor.submit(job);
        jobExecutor.shutdown();
        
        AtomicLong cnt = new AtomicLong(0);
        feedData().stream().map(obj->{        	
        	return new GenericRecord<GenericPOJO>(new Header(cnt.getAndIncrement(), "customized feed", new Date()), obj);
        }).forEach(r->{
        	try {
				workQueue.put(r);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
        });
        
        workQueue.put(new PoisonRecord());
        
        System.out.println(reportFuture.get());
	}
	
	public static List<GenericPOJO> feedData() {
		List<GenericPOJO> feeds = Lists.newArrayList();
		
		IntStream.range(1, 30).forEach(i->{
			GenericPOJO obj = new GenericPOJO();
			obj.id = i;
			obj.msg = "msg " + i;
			feeds.add(obj);
		});
		
		return feeds;
	}
}

class GenericPOJO {
	public int id;
	public String msg;	
}
