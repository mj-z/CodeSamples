package mjz.javasamples.java8.filelock;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.util.Date;

public class Lock {
	public static void main(String[] args) {

		RandomAccessFile lockFile = null;
		FileLock lock = null;

		try {
		//try (RandomAccessFile lockFile = new RandomAccessFile("target/lockfile.dat", "rw")) {
			lockFile = new RandomAccessFile("target/lockfile.dat", "rw");
			FileChannel channel = lockFile.getChannel();
			try {
				System.out.println("Getting the Lock!");
				lock = channel.lock();
				
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println("Got the Lock!");

				lockFile.seek(0);				
				byte[] buf = new byte[(int) lockFile.length()];
				lockFile.read(buf);
				System.out.println(new String(buf));				
				
				lockFile.seek(0);	
				lockFile.write(("this is 2 test " + System.currentTimeMillis()).getBytes());
				
				lockFile.seek(0);	
				buf = new byte[(int) lockFile.length()];
				lockFile.read(buf);
				System.out.println(new String(buf));

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != lock) {
				try {
					lock.release();
					System.out.println("Released The Lock" + new Date().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(null != lockFile) {
				try {
					lockFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
