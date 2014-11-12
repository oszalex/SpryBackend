package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import rest.domain.Happening;
import rest.domain.Invitation;
import rest.domain.User;
import rest.service.HappeningRepository;
import rest.service.UserRepository;

import java.util.Arrays;

@ComponentScan
@EnableAutoConfiguration
public class Application {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HappeningRepository happeningRepository;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        User y;
        Happening x;
        Invitation z;
        /*System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        */
    }
}