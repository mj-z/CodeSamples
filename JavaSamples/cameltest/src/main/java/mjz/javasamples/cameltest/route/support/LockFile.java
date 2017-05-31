package mjz.javasamples.cameltest.route.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LockFile {
	
	private String fileName = "target/filelock.dat";
	
	String id = UUID.randomUUID().toString();

	public boolean lock() {

		boolean lock = false;
		
		RandomAccessFile file = null;
		FileLock fileLock = null;

		try {
			file = new RandomAccessFile(fileName, "rw");;
			FileChannel channel = file.getChannel();
			try {
				fileLock = channel.tryLock();								

				if(null != fileLock) {			
					
					String fileContent = getFileContent(file);
					if(StringUtils.isBlank(fileContent)) {
						updateCheckPoint(file);
						lock = true;
					} else {
						String[] eles = fileContent.split("\\|");
						String master = eles[0];
						long lastCheckOutTiming = Long.parseLong(eles[1]);
						
						if(id.equals(master)) {
							updateCheckPoint(file);
							lock = true;
						} else if(System.currentTimeMillis() > lastCheckOutTiming + 15 * 1000) {														
							updateCheckPoint(file);
							lock = true;
						}						
					}
				}					
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != fileLock) {
				try {					
					fileLock.release();					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return lock;
	}

	private void updateCheckPoint(RandomAccessFile file) throws IOException {
		String fileContent;
		fileContent = id + "|" + System.currentTimeMillis();
		writeFileContent(file, fileContent);
	}

	public static String getFileContent(RandomAccessFile file) throws IOException {
		file.seek(0);				
		byte[] buf = new byte[(int) file.length()];
		file.read(buf);
		return new String(buf);
	}
	
	public static void writeFileContent(RandomAccessFile file, String content) throws IOException {
		file.seek(0);	
		file.write(content.getBytes());
	}
}
