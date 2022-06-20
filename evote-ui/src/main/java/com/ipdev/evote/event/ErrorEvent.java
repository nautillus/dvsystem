package com.ipdev.evote.event;

import com.ipdev.evote.ui.control.ErrorController;
import javafx.stage.Stage;

public class ErrorEvent extends SceneEvent{
    public ErrorEvent(){}
    public ErrorEvent(Stage stage) {
        super(stage, ErrorController.class);
    }
}
