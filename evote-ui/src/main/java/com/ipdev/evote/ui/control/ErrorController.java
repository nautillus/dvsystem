package com.ipdev.evote.ui.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/fxml/error.fxml")
public class ErrorController {
    @FXML
    private Label msg;
}
