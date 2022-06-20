package com.ipdev.evote.ui.control;

import com.ipdev.evote.event.EventPublisher;
import com.ipdev.evote.event.SceneEvent;
import com.ipdev.evote.grpc.client.FiscalRequest;
import com.ipdev.evote.grpc.client.FiscalResponse;
import com.ipdev.evote.grpc.client.GrpcAppClient;
import io.grpc.Status;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@FxmlView("/fxml/welcome.fxml")
public class WelcomeController {
    @FXML
    private Button fireScanEvent;
    private final FxWeaver fxWeaver;

    @Autowired
    private EventPublisher publisher;
    @Autowired
    @Qualifier("welcomeEvent")
    private SceneEvent event;
    @Autowired
    @Qualifier("blankEvent")
    private SceneEvent nextSceneEvent;

    @Autowired
    private GrpcAppClient grpcAppClient;

    private List<String> tmpFiscal = Arrays.asList(
            "PVL RIU 87D09 Z140M",
            "PVL NDR 76D17 Z140N",
            "NTL PLV 68E62 Z140A",
            "LHI CBN 83S22 Z140M");

    public WelcomeController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        fireScanEvent.setOnAction(action -> {
            FxControllerAndView<LoadingController, StackPane> loading = fxWeaver.load(LoadingController.class);
            Collections.shuffle(tmpFiscal);
            ExecutorService es = Executors.newFixedThreadPool(1);
            CompletableFuture.supplyAsync(() -> {
                FiscalResponse fiscalResponse;
                try {
                    Object userData = fireScanEvent.getScene().getUserData();
                    String fiscalId = userData != null && (boolean) userData ? "PLV 1111 NOT EXISTS" : tmpFiscal.get(0);
                    System.out.println("Event fired with fiscalId: " + fiscalId);
                    fiscalResponse =
                            grpcAppClient.getService().check(
                                    FiscalRequest.newBuilder()
                                            .setFiscalId(fiscalId)
                                            .build());
                    //wait a few seconds
//                    TimeUnit.SECONDS.sleep(2);

//                    throw new RuntimeException("bla");
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
                return fiscalResponse;
            }, es).thenAccept(response -> {
                    Platform.runLater(() -> {
                        redirect(loading);
                    });
            }).exceptionally(ex -> {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
                Status status = Status.fromThrowable(ex);
                Platform.runLater(() -> {
                    nonRedirect(loading, status.getCode().toString(), status.getDescription());
                });
                return null;
            });
        });
    }

    private void nonRedirect(FxControllerAndView<LoadingController, StackPane> loading, String code, String message) {
        loading.getController().stopTimer();
        loading.getController().showErrorMessage(code + ": " + message);
    }

    private void redirect(FxControllerAndView<LoadingController, StackPane> loading) {
        loading.getController().stopTimer();
        loading.getController().closeStg();
        Stage window = (Stage) fireScanEvent.getScene().getWindow();
        window.setUserData(tmpFiscal.get(0));
        event.setStage(window);
        event.setNextScene(nextSceneEvent);
        publisher.publishEvent(event);
    }
}
