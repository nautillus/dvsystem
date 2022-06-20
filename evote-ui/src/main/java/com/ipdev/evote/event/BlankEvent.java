package com.ipdev.evote.event;

import com.ipdev.evote.ui.control.BlankController;
import javafx.stage.Stage;

public class BlankEvent extends SceneEvent{
    public BlankEvent(){}
    public BlankEvent(Stage stage) {
        super(stage, BlankController.class);
    }
}
