package com.scottlogic.training;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/*
  This starts up both the Spring Boot MVC server and the socketIO server
  (provided by netty-socketio: https://github.com/mrniko/netty-socketio)
 */
@SpringBootApplication
@RestController
public class Application {

    @Value("${rt.server.host}")
    private String host;

    @Value("${rt.server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

}
