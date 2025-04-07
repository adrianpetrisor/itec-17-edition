package com.itec.application.events;

import com.itec.application.entities.ApplicationEntity;
import com.itec.application.repository.ApplicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<ApplicationEntity> applicationEntities = applicationsRepository.findAll();
        System.out.println(applicationEntities.size());
    }
}
