package com.kramar.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DataApplication implements CommandLineRunner {

    @Autowired
    PasswordEncoder encoder;

    public static void main(final String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

    @Override
    public void run(final String... strings) throws Exception {
//        System.out.println(encoder.encode("12345"));
    }
}
