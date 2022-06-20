package com.ipdev.evote.event;

import com.ipdev.evote.ui.control.LoadingController;
import javafx.stage.Stage;

public class LoadingEvent extends SceneEvent{
    public LoadingEvent(){}
    public LoadingEvent(Stage stage) {
        super(stage, LoadingController.class);
    }
}
