package com.ipdev.evote.event;

import com.ipdev.evote.ui.control.WelcomeController;
import javafx.stage.Stage;

public class WelcomeEvent extends SceneEvent{
    public WelcomeEvent(){}
    public WelcomeEvent(Stage stage) {
        super(stage, WelcomeController.class);
    }
}
