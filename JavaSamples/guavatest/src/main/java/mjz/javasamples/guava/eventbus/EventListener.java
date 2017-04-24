package mjz.javasamples.guava.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class EventListener {

    private int eventsHandled;
    private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

    @Subscribe
    //@AllowConcurrentEvents
    public void stringEvent(String event) {
        LOG.info("do event [" + event + "]");
        eventsHandled++;
    }

    @Subscribe
    public void someCustomEvent(CustomEvent customEvent) {
        LOG.info("do event [" + customEvent.getAction() + "]");
        eventsHandled++;
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        LOG.info("unhandled event [" + deadEvent.getEvent() + "]");
        eventsHandled++;
    }

    public int getEventsHandled() {
        return eventsHandled;
    }

    public void resetEventsHandled() {
        eventsHandled = 0;
    }
}
