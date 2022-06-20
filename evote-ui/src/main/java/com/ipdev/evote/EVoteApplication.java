package com.ipdev.evote;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EVoteApplication {

    public static void main(String[] args) {
        Application.launch(EVoteFxApplication.class, args);
    }

}
