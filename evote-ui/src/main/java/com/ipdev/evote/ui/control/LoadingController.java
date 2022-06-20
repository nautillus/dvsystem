package com.ipdev.evote.ui.control;

import com.ipdev.evote.timer.TimerHelper;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/fxml/loading.fxml")
public class LoadingController {

    private Stage stage;
    private Timeline timeline;

    @FXML
    private VBox root;
    @FXML
    private ProgressBar loading;
    @FXML
    private Button closeBtn;
    @FXML
    private Label errorLabel;

    @Autowired
    private TimerHelper timerHelper;

    @Value("${application.modal.width}")
    private String width;
    @Value("${application.modal.height}")
    private String height;
    @Value("${timeout.loading.screen}")
    private String timeout;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        this.stage.setWidth(Double.valueOf(width));
        this.stage.setHeight(Double.valueOf(height));
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(new Scene(root));
        toggleProgressPanelComponents(false);
        Integer delay = Integer.valueOf(timeout);
        IntegerProperty seconds = new SimpleIntegerProperty(delay);
        loading.progressProperty().bind(seconds.divide(60.0));
        this.timeline = timerHelper.startTimerAsc(delay, ae -> showErrorMessage("Timeout exception, please retry later."), seconds);
        closeBtn.setOnAction(e -> this.stage.close());

        this.stage.show();
    }

    public void stopTimer(){
        this.timeline.stop();
    }

    public void closeStg(){
        this.stage.close();
    }

    public void showErrorMessage(String msg) {
        errorLabel.setText(msg);
        toggleProgressPanelComponents(true);
    }

    private void toggleProgressPanelComponents(boolean v) {
        closeBtn.setVisible(v);
        errorLabel.setVisible(v);
    }
}
