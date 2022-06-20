package com.ipdev.evote.event;

import javafx.stage.Stage;

public abstract class SceneEvent {
    private SceneEvent nextScene;
    private Stage stage;
    private Class cls;

    public SceneEvent getNextScene() {
        return nextScene;
    }

    public void setNextScene(SceneEvent nextScene) {
        this.nextScene = nextScene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public SceneEvent(){
    }
    public SceneEvent(Stage stage, Class cls) {
        this.stage = stage;
        this.cls = cls;
    }
}
