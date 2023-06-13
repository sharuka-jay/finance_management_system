package com.example.interest_service.controller;

import com.example.interest_service.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterestController {
    @Autowired
    InterestService interestService;

//    @GetMapping("/interest)
//    public void calculateInterest() {
//        try {
//
//
//        } catch (Exception e) {
//
//        }
//    }

    public void calculateInterest() {
        try {
            interestService.calculateInterest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
