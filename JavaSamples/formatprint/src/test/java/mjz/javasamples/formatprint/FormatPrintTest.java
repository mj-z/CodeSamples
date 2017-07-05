package mjz.javasamples.formatprint;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import mjz.javasamples.formatprint.po.TestInnerObject;
import mjz.javasamples.formatprint.po.TestObject;

@SuppressWarnings("deprecation")
public class FormatPrintTest {
	
	Logger log = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("deprecation")
	List<TestObject> generateTestObjects() {
		List<TestObject> list = Lists.newArrayList();
		
		IntStream.range(0, 4).boxed().forEach(i->{
			TestObject obj = new TestObject();
			obj.setCreatetime(LocalDateTime.now());
			obj.setDesc(RandomStringUtils.randomAlphanumeric(25));
			obj.setId(RandomUtils.nextInt(10000, 20000));
			obj.setLabel(RandomStringUtils.randomAlphanumeric(10));
			obj.setLongId(RandomUtils.nextLong(4646416113L, 79879413164L));
			obj.setAmount(new BigDecimal("" + RandomUtils.nextDouble(75656944654.23, 789411133356564.32)));
			obj.setTested(System.nanoTime() % 2 == 0 ? true : false);
			
			TestInnerObject inner1 = new TestInnerObject();
			inner1.setId(RandomStringUtils.randomAlphanumeric(7));
			inner1.setDesc(RandomStringUtils.randomAlphanumeric(50));
			
			TestInnerObject inner2 = new TestInnerObject();
			inner2.setId(RandomStringUtils.randomAlphanumeric(3));
			inner2.setDesc(RandomStringUtils.randomAlphanumeric(30));

			obj.setInner1(inner1);
			obj.setInner2(inner2);
			
			list.add(obj);
		});
		
		return list;
	}
	
	@Test
	public void testPrint() {
		List<TestObject> objs = generateTestObjects();
		TabularFormatter<TestObject> ft = new TabularFormatter<>();
		System.out.println(ft.format(objs));
		
		log.info("{}", ft.format(objs));
	}
}
