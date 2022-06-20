package com.ipdev.evote.ui.control;

import com.ipdev.evote.event.EventPublisher;
import com.ipdev.evote.event.SceneEvent;
import com.ipdev.evote.grpc.client.FiscalRequest;
import com.ipdev.evote.grpc.client.GrpcAppClient;
import com.ipdev.evote.grpc.client.PartyRequest;
import com.ipdev.evote.grpc.client.PartyResponse;
import com.ipdev.evote.grpc.client.RowBlank;
import com.ipdev.evote.grpc.client.VoteRequest;
import com.ipdev.evote.grpc.client.VoteResponse;
import com.ipdev.evote.model.RowBlankDto;
import io.grpc.Status;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.protobuf.util.Timestamps.fromMillis;
import static java.lang.System.currentTimeMillis;
@Component
@FxmlView("/fxml/blank.fxml")
public class BlankController {

    private final FxWeaver fxWeaver;

    @FXML
    private ListView<RowBlankDto> vote;
    @FXML
    private Button cancel;
    @FXML
    private Button confirm;

    @Autowired
    private EventPublisher publisher;

    @Autowired
    @Qualifier("blankEvent")
    private SceneEvent event;

    @Autowired
    @Qualifier("goodbyeEvent")
    private SceneEvent nextSceneEvent;

    @Autowired
    @Qualifier("welcomeEvent")
    private SceneEvent cancelSceneEvent;

    @Autowired
    private GrpcAppClient grpcAppClient;

    private ObservableList<RowBlankDto> rowBlankDtoObservableList;

    public BlankController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
        rowBlankDtoObservableList = FXCollections.observableArrayList();
    }

    private class OnToggleHandler implements ChangeListener<Toggle> {
        @Override
        public void changed(ObservableValue<? extends Toggle> ov, Toggle oldT, Toggle newT) {
            //TODO fix this null pointer inside the listView when we scroll down/up very fast newT became null
            if (newT != null) {
                RadioButton pressed = (RadioButton) newT;
                String id = pressed.getId();
                newT.getToggleGroup().getSelectedToggle().setUserData(Byte.valueOf(id));
                confirm.setDisable(false);
            }
        }
    }

    @FXML
    public void initialize() {
        //fetch all parties
        PartyResponse response = grpcAppClient.getService().getAvailableChoices(PartyRequest.newBuilder()
                .setToken("blb bla b-la-valid-token")
                .build());
        List<RowBlankDto> list = new ArrayList<>();
        for (RowBlank rb : response.getVotedList()) {
            list.add(new RowBlankDto((byte) rb.getId(), rb.getImage().toByteArray(), rb.getLeader(), rb.getParty(), false));
        }
        rowBlankDtoObservableList.clear();
        rowBlankDtoObservableList.addAll(list);

        vote.setItems(rowBlankDtoObservableList);
        final ToggleGroup toggleGroup = new ToggleGroup();
        final OnToggleHandler stampHandler = new OnToggleHandler();
        toggleGroup.selectedToggleProperty().addListener(stampHandler);
        vote.setCellFactory(blankListView -> new RowController(toggleGroup));
        confirm.setDisable(true);
        confirm.setOnAction(action -> {
            FxControllerAndView<LoadingController, StackPane> loading = fxWeaver.load(LoadingController.class);
            // make backend calls and if successful stop timer and redirect
            // back end calls

            ExecutorService es = Executors.newFixedThreadPool(1);
            CompletableFuture.supplyAsync(() -> {
                VoteResponse voteResponse = null;
                Object userData = toggleGroup.getSelectedToggle().getUserData();
                try {
                    voteResponse =
                            grpcAppClient.getService().vote(VoteRequest.newBuilder()
                                    .setInserted(fromMillis(currentTimeMillis()))
                                    .setVoted(RowBlank.newBuilder()
                                                      .setId((Byte) userData)
                                                      .build())
                                    .build());
                    //wait a few seconds
                    TimeUnit.SECONDS.sleep(2);
//                    throw new RuntimeException("bla");
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                return voteResponse;
            }, es).thenAccept(r1 -> {
                if(r1.getSuccess()) {
                    // Update voter by setting flag to TRUE
                    Object fiscalId = confirm.getScene().getWindow().getUserData();
                    grpcAppClient.getService().finishVote(FiscalRequest.newBuilder().setFiscalId(fiscalId.toString()).build());
                } else {
                    throw new IllegalStateException("Voting operation failed, please try later!");
                }
            }).thenAccept(r2 -> {
                        Platform.runLater(() -> {
                            redirect(loading);
                        });
            }).exceptionally(ex -> {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
                Status status = Status.fromThrowable(ex);
                Platform.runLater(() -> {
                    // if error from backend occurs stop timer , show error message
                    nonRedirect(loading, status.getCode().toString(), status.getDescription());
                });
                return null;
            });
        });
        cancel.setOnAction(action -> {
            redirect();
        });
    }
    private void nonRedirect(FxControllerAndView<LoadingController, StackPane> loading, String code, String message) {
        loading.getController().stopTimer();
        loading.getController().showErrorMessage(code + ": " + message);
    }

    private void redirect(FxControllerAndView<LoadingController, StackPane> loading) {
        loading.getController().stopTimer();
        loading.getController().closeStg();
        Stage window = (Stage) confirm.getScene().getWindow();
        event.setStage(window);
        event.setNextScene(nextSceneEvent);
        publisher.publishEvent(event);
    }

    private void redirect() {
        Stage window = (Stage) cancel.getScene().getWindow();
        event.setStage(window);
        event.setNextScene(cancelSceneEvent);
        publisher.publishEvent(event);
    }
}
