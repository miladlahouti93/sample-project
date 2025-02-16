package com.example.infrastructure;

import com.example.common.BeanContext;
import com.example.controller.Controller;

public class Main {
    public static void main(String[] args) throws Exception {
        BeanContext context = new BeanContext("com.example.controller");
        Controller controller = context.getBean(Controller.class);
        controller.execute();
    }
}