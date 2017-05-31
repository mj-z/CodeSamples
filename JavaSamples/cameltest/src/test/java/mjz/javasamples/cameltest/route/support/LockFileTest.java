package mjz.javasamples.cameltest.route.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LockFileTest {
	
	RandomAccessFile file = null;
	
	@Before
	public void setup() throws FileNotFoundException {
		file = new RandomAccessFile("target/filetest.txt", "rw");
	}
	
	@After
	public void closeFile() throws IOException {
		if(file != null) {
			file.close();
			file = null;
		}
			
	}
	
	@Test
	public void testGetFileContent() throws Exception {
		String expected = FileUtils.readFileToString(new File("target/filetest.txt"), "UTF-8");
		String actual = LockFile.getFileContent(file);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testWriteFileContent() throws Exception {
		String expected = UUID.randomUUID().toString();
		
		LockFile.writeFileContent(file, expected);
		closeFile();
		
		String actual = FileUtils.readFileToString(new File("target/filetest.txt"), "UTF-8");
		Assert.assertEquals(expected, actual);
	}

}
