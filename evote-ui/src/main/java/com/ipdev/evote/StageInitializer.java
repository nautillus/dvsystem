package com.ipdev.evote;

import com.ipdev.evote.EVoteFxApplication.StageReadyEvent;
import com.ipdev.evote.ui.control.WelcomeController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final FxWeaver fxWeaver;

    @Value("${application.width:800}")
    private String width;
    @Value("${application.height:600}")
    private String height;
    @Value("${application.window.decorate:false}")
    private String decorate;

    @Value("${spring.simulate.not_found.fiscal:false}")
    private boolean tmpSimulateInvalidFiscalID;
    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            FxWeaver fxWeaver) {
        this.applicationTitle = applicationTitle;
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        stage.initStyle(Boolean.valueOf(decorate) ? StageStyle.DECORATED : StageStyle.UNDECORATED);
        stage.setScene(new Scene(fxWeaver.loadView(WelcomeController.class), Double.valueOf(width), Double.valueOf(height)));
        stage.getScene().setUserData(tmpSimulateInvalidFiscalID);
        stage.setTitle(applicationTitle);
        stage.show();
    }
}
