package com.example.ui_service.controller;

import com.example.account_service.model.Account;
import com.example.ui_service.dto.Register;
import com.example.ui_service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Controller
public class InterfacesController {

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private WebClient webClient;

    /**************  user  service   ********/

    @GetMapping("/login")
    public String loginPage() {
        try {
            return "login";
        } catch (Exception e) {
            return "404";
        }

    }

    @GetMapping("/register")
    public String registerPage() {
        try {
            return "register";
        } catch (Exception e) {
            return "404";
        }

    }


    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") Register register) {
        try { // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HTTP entity with the user object and headers
            HttpEntity<Register> requestEntity = new HttpEntity<>(register, headers);

            ResponseEntity<Integer> responseEntity = restTemplate.exchange(
                    "http://localhost:7078/register",
                    HttpMethod.POST,
                    requestEntity,
                    Integer.class
            );

            // Retrieve the response body containing a string
            Integer responseString = responseEntity.getBody();

            return "login";
        } catch (Exception e) {
            return "404";
        }

    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") Register register, Model model) {
        try { // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HTTP entity with the user object and headers
            HttpEntity<Register> requestEntity = new HttpEntity<>(register, headers);

            ResponseEntity<User> responseEntity = restTemplate.exchange(
                    "http://localhost:7078/login",
                    HttpMethod.POST,
                    requestEntity,
                    User.class
            );

            // Retrieve the response body containing a string
            User responseUser = responseEntity.getBody();

            int id = responseUser.getUser_id();


            if (responseUser != null) {
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON);

                // Create the request entity with the parameters and headers
                HttpEntity<Object> requestEntity2 = new HttpEntity<>(Map.of("user_id", id), headers2);


                ResponseEntity<List<Account>> responseEntity2 = restTemplate.exchange(
                        "http://localhost:7079/seachAllAccountsbyUserID",
                        HttpMethod.POST,
                        requestEntity2,
                        new ParameterizedTypeReference<List<Account>>() {
                        }
                );
                // Assuming the response contains the new saving account ID
                List<Account> accounts = responseEntity2.getBody();

                if (accounts != null && !accounts.isEmpty()) {
                    model.addAttribute("account", accounts);
                }

                model.addAttribute("user_id", responseUser.getUser_id());
                return "home";
            } else {
                return "redirect:/login?error";
            }
        } catch (Exception e) {
            return "404";
        }
    }


}
