package com.ipdev.evote.timer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class TimerHelper {

    public Timeline startTimerDesc(Integer delay, EventHandler<ActionEvent> onFinished, IntegerProperty seconds) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delay + 1),
                onFinished,
                new KeyValue(seconds, 0))
        );
        timeline.play();
        return timeline;
    }

    public Timeline startTimerAsc(Integer delay, EventHandler<ActionEvent> onFinished, IntegerProperty seconds) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
                new KeyFrame(Duration.seconds(delay),
                        onFinished,
                        new KeyValue(seconds, 60))
        );
        timeline.play();
        return timeline;
    }
}
