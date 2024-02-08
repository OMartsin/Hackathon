package trandafyl.dev.hackathontest.events.listeners;


import trandafyl.dev.hackathontest.events.models.UpdateEvent;

public interface UpdateEventListener {
    void update(UpdateEvent event);
}