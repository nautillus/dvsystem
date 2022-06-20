package com.ipdev.evote.event;

import javafx.scene.Scene;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SceneEventListener {

    @Value("${application.width}")
    private String width;
    @Value("${application.height}")
    private String height;

    private final FxWeaver fxWeaver;
    public SceneEventListener(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @EventListener
    void handleSceneEvent(SceneEvent event) {
        event.getStage().setScene(new Scene(fxWeaver.loadView((Class<Object>) event.getNextScene().getCls()), Double.valueOf(width), Double.valueOf(height)));
    }
}
