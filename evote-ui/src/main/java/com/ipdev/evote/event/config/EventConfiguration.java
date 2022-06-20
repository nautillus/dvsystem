package com.ipdev.evote.event.config;

import com.ipdev.evote.event.BlankEvent;
import com.ipdev.evote.event.ErrorEvent;
import com.ipdev.evote.event.GoodByeEvent;
import com.ipdev.evote.event.LoadingEvent;
import com.ipdev.evote.event.SceneEvent;
import com.ipdev.evote.event.WelcomeEvent;
import com.ipdev.evote.ui.control.BlankController;
import com.ipdev.evote.ui.control.ErrorController;
import com.ipdev.evote.ui.control.GoodByeController;
import com.ipdev.evote.ui.control.LoadingController;
import com.ipdev.evote.ui.control.WelcomeController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    @Qualifier("welcomeEvent")
    SceneEvent welcomeEvent(){
        SceneEvent event = new WelcomeEvent();
        event.setCls(WelcomeController.class);
        return event;
    }

    @Bean
    @Qualifier("blankEvent")
    SceneEvent blankEvent(){
        SceneEvent event = new BlankEvent();
        event.setCls(BlankController.class);
        return event;
    }

    @Bean
    @Qualifier("goodbyeEvent")
    SceneEvent goodbyeEvent(){
        SceneEvent event = new GoodByeEvent();
        event.setCls(GoodByeController.class);
        return event;
    }

    @Bean
    @Qualifier("loadingEvent")
    SceneEvent loadingEvent(){
        SceneEvent event = new LoadingEvent();
        event.setCls(LoadingController.class);
        return event;
    }

    @Bean
    @Qualifier("errorEvent")
    SceneEvent errorEvent(){
        SceneEvent event = new ErrorEvent();
        event.setCls(ErrorController.class);
        return event;
    }
}
