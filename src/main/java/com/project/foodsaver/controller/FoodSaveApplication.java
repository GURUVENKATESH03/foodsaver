package com.project.foodsaver.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FoodSaveApplication {

    @GetMapping("/landingPage")
    public void getHomePage(){
        return;
    }

    @GetMapping("/hello")
    public Map<String,Object> frontAndBackendCheck(){
        Map<String,Object> response = new HashMap<>();
        response.put("success","200");
        response.put("message","Front to backend connection established successfully");
        return response;
    }
}
