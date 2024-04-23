package com.pg.backend;

import com.pg.backend.common.beans.IService;
import com.pg.backend.common.util.SpringContextKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Map;

@Slf4j
@SpringBootApplication
@Configuration
public class BackendApplication {

    public static ConfigurableApplicationContext applicationContext;
    public static void main(String[] args) throws Exception{
        SpringApplication app=new SpringApplication(BackendApplication.class);
        applicationContext=app.run(args);
        Environment env = applicationContext.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t"+
                        "Doc: \thttp://{}:{}/doc.html\n"+
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
        Map<String, IService> beansOfType = SpringContextKit.getBeansOfType(IService.class);
        for (Map.Entry<String, IService> t : beansOfType.entrySet()) {
            System.out.println(t.getKey()+"..."+t.getValue());
        }
    }
    @Bean
    @Lazy
    public Environment getEnv() {
        return applicationContext.getEnvironment();
    }
}

