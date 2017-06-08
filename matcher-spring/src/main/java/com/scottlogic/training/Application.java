package com.scottlogic.training;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@SpringBootApplication
@RestController
public class Application {

    @RequestMapping(value = "/health", method = GET, produces = "text/plain")
    public ResponseEntity<String> getHealth() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .properties("server.port=8080").run(args);
    }

}
