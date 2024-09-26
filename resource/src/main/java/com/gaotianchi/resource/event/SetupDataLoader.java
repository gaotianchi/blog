package com.gaotianchi.resource.event;

import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepo userRepo;

    @Autowired
    public SetupDataLoader(UserRepo userRepo) {

        this.userRepo = userRepo;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createBloggerIfNotFound();
    }

    private void createBloggerIfNotFound() {
        UserEntity userEntity = userRepo.findByUsername("6159984@gmail.com");
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setPenName("高天驰");
            userEntity.setUsername("6159984@gmail.com");
            userEntity.setTimeZone(TimeZone.getDefault());
            userEntity.setProfile("目前在追剧《流人》，每周末晚上喜欢看非凡-迪安的绝地求生直播，目前在积极地找工作。");
            userRepo.save(userEntity);
        }
    }
}
