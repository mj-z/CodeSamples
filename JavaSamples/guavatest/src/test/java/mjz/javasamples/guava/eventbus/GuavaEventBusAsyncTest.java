package mjz.javasamples.guava.eventbus;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

public class GuavaEventBusAsyncTest {

    private EventListener listener1, listener2;
    private EventBus eventBus;

    @Before
    public void setUp() {
        eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        listener1 = new EventListener();
        listener2 = new EventListener();

        eventBus.register(listener1);
        eventBus.register(listener2);
    }

    @After
    public void tearDown() {
        eventBus.unregister(listener1);
        eventBus.unregister(listener2);
    }

    @Test
    public void givenStringEvent_whenEventHandled_thenSuccess() {
        listener1.resetEventsHandled();
        listener2.resetEventsHandled();

        eventBus.post("String Event1");
        eventBus.post("String Event2");
        
        try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        
        assertEquals(2, listener1.getEventsHandled());
    }
    
    @Test
    public void givenIntEvent_whenEventHandled_thenSuccess() {
        listener1.resetEventsHandled();
        listener2.resetEventsHandled();

        eventBus.post(1);        
        
        try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        
        eventBus.post(2);
        
        try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        
        assertEquals(2, listener1.getEventsHandled());
    }

    

}
