package com.wojcik.lukasz.melanomacheckerserver.model.initData;

import com.wojcik.lukasz.melanomacheckerserver.model.entity.Mole;
import com.wojcik.lukasz.melanomacheckerserver.model.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadH2Database {

    @Bean
    CommandLineRunner initDatabase(ImageRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Mole("img1")));
            log.info("Preloading " + repository.save(new Mole("img2")));
        };
    }
}
