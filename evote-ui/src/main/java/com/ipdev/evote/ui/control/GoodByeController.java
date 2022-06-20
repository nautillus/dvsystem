package com.ipdev.evote.ui.control;

import com.ipdev.evote.event.EventPublisher;
import com.ipdev.evote.event.SceneEvent;
import com.ipdev.evote.timer.TimerHelper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.text.Element;

import java.io.File;

@Component
@FxmlView("/fxml/goodbye.fxml")
public class GoodByeController {
    @FXML
    private Label count;
    @FXML
    private ImageView iView;
    @Autowired
    private EventPublisher publisher;
    @Autowired
    private TimerHelper timerHelper;

    @Autowired
    @Qualifier("goodbyeEvent")
    private SceneEvent event;

    @Autowired
    @Qualifier("welcomeEvent")
    private SceneEvent nextSceneEvent;

    @Value("${timeout.redirect.welcome.screen}")
    private String timeout;

    @FXML
    public void initialize() {
        Image image = new Image("/img/check-green.gif");
        iView.setImage(image);
        iView.setFitWidth(100);
        iView.setFitHeight(100);
        iView.setX(350);
        iView.setY(150);
        Integer delay = Integer.valueOf(timeout);
        IntegerProperty seconds = new SimpleIntegerProperty(delay);
        count.textProperty().bind(Bindings.format("%s seconds", seconds.asString()));
        timerHelper.startTimerDesc(delay, ae -> redirect(), seconds);
    }

    private void redirect() {
        Stage window = (Stage) count.getScene().getWindow();
        event.setStage(window);
        event.setNextScene(nextSceneEvent);
        publisher.publishEvent(event);
    }
}
