package com.shopping;

import com.shopping.service.AService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
@Slf4j
@Getter
@Setter
public class App {
    private String name;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.shopping");
        AService aService = annotationConfigApplicationContext.getBean(AService.class);
        aService.sayHello();
        log.debug("1234567890");
    }
}
