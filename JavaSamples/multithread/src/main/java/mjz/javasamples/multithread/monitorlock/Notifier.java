package mjz.javasamples.multithread.monitorlock;

public class Notifier implements Runnable {

	private Message msg;

	public Notifier(Message msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " started");
		try {
			Thread.sleep(1000);
			synchronized (msg) {
				System.out.println(name + " doing");
				msg.setMsg(name + " Notifier work done");
				//msg.notify();
				msg.notifyAll();
				
				long start = System.currentTimeMillis();
				while(true) {
					if(System.currentTimeMillis() - start > 1000 * 5)
						break;
				}
				System.out.println(name + " done");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}