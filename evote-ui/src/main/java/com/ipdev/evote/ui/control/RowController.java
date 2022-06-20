package com.ipdev.evote.ui.control;

import com.ipdev.evote.model.RowBlankDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

//@Component
//@Scope("prototype")
//@FxmlView("/fxml/row.fxml")
public class RowController extends ListCell<RowBlankDto> {
    @FXML
    private Pane iconParty;
    @FXML
    private Label leader;
    @FXML
    private Label party;
    @FXML
    private RadioButton stamp;
    @FXML
    private GridPane gridPane;

    private ToggleGroup toggleGroup;
    private FXMLLoader mLLoader;

    public RowController(ToggleGroup toggleGroup){
        this.toggleGroup = toggleGroup;
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("/fxml/row.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    protected void updateItem(RowBlankDto rowBlankDto, boolean empty) {
        super.updateItem(rowBlankDto, empty);
        if(!empty || rowBlankDto != null) {
            if(rowBlankDto.image().length > 0) {
                InputStream targetStream = new ByteArrayInputStream(rowBlankDto.image());
                BackgroundImage myBI= new BackgroundImage(new Image(targetStream),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                iconParty.setBackground(new Background(myBI));
            }
            leader.setText(rowBlankDto.leader());
            party.setText(rowBlankDto.party());
            stamp.setId(String.valueOf(rowBlankDto.id()));
            stamp.setToggleGroup(toggleGroup);
            stamp.setSelected(toggleGroup.getSelectedToggle() != null
                    && (byte) toggleGroup.getSelectedToggle().getUserData() == rowBlankDto.id() || rowBlankDto.voted());
            setGraphic(gridPane);
        } else {
            setText(null);
            setGraphic(null);
        }

    }
}
