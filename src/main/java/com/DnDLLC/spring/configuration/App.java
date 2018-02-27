package com.DnDLLC.spring.configuration;

import com.DnDLLC.spring.models.Dot;
import com.DnDLLC.spring.models.User;
import com.DnDLLC.spring.models.UserRepository;
import com.DnDLLC.spring.models.DotRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;


@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.DnDLLC.spring.models")
@ComponentScan(basePackages = {"com.DnDLLC.spring"})
@EntityScan("com.DnDLLC.spring.models")
public class App {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DotRepository dotRepository;

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            User user = new User("ya", "admin");
            userRepository.save(user);
        };
    }
}
