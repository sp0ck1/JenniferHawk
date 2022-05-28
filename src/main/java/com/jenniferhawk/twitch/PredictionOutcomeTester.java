package com.jenniferhawk.twitch;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.pubsub.events.PredictionUpdatedEvent;


//TODO: Delete this class
public class PredictionOutcomeTester {

    public PredictionOutcomeTester(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(PredictionUpdatedEvent.class, event -> onUpdate(event));
    }

    void onUpdate(PredictionUpdatedEvent event) {
        event.getEvent().getOutcomes().forEach(predictionOutcome -> predictionOutcome.getColor());
    }
}
