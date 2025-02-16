package com.example.controller;

import com.example.annotation.Autowired;
import com.example.annotation.Component;

@Component
public class Controller {
    @Autowired
    private com.example.controller.Service Service;

    public void execute() {
        Service.sayHello();
    }
}
