package com.example.controller;

import com.example.controller.annotation.Component;

@Component
public class Service {
    public void sayHello() {
        System.out.println("Hello from MyService!");
    }
}
