package com.example.controller;

import com.example.controller.annotation.Autowired;
import com.example.controller.annotation.Component;

@Component
public class Controller {
    @Autowired
    private com.example.controller.Service Service;

    public void execute() {
        Service.sayHello();
    }
}
