package mjz.javasamples.java8.filelock;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Read {
	public static void main(String[] args) {

		RandomAccessFile lockFile = null;
		

		try {
		//try (RandomAccessFile lockFile = new RandomAccessFile("target/lockfile.dat", "rw")) {
			lockFile = new RandomAccessFile("target/lockfile.dat", "rw");
			
			try {
				

				lockFile.seek(0);				
				byte[] buf = new byte[(int) lockFile.length()];
				lockFile.read(buf);
				System.out.println(new String(buf));				
				

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			
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
