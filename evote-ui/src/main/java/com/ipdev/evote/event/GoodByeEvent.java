package com.ipdev.evote.event;

import com.ipdev.evote.ui.control.GoodByeController;
import javafx.stage.Stage;

public class GoodByeEvent extends SceneEvent{
    public GoodByeEvent(){}
    public GoodByeEvent(Stage stage) {
        super(stage, GoodByeController.class);
    }
}
