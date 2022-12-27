package com.example.taskscheduler;


import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class SchedulingSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchedulingSystemApplication.class, args);
  }
}