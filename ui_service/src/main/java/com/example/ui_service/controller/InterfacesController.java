package com.example.ui_service.controller;

import com.example.account_service.model.Account;
import com.example.ui_service.dto.Register;
import com.example.ui_service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
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

            if (responseUser != null) {
                model.addAttribute("user_id", responseUser.getUser_id());
                return "home";
            } else {
                return "redirect:/login?error";
            }
        } catch (Exception e) {
            return "404";
        }
    }

    /**************  account  service   ********/

    @GetMapping("/home")
    public String homePage(@RequestParam("param") int id, Model model) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the parameters and headers
            HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("user_id", id), headers);


            ResponseEntity<List<Account>> responseEntity = restTemplate.exchange(
                    "http://localhost:7079/seachAllAccountsbyUserID",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Account>>() {}
            );
            // Assuming the response contains the new saving account ID
            List<Account> accounts = responseEntity.getBody();

            if (accounts!=null){
                model.addAttribute("account", accounts);
            }

            model.addAttribute("user_id", id);
            return "home";
        } catch (Exception e) {
            return "404";
        }
    }


    @GetMapping("/newSaving/{id}")
    public String newSavingAccountpage(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("id", id);
            return "newSaving";
        } catch (Exception e) {
            return "404";
        }
    }

    @PostMapping("/newsavingaccount")
    public String saveSavingAccount(@RequestParam("initialAmount") BigDecimal balance, @RequestParam("userId") int user_id, Model model) {
        try {// Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the parameters and headers
            HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("balance", balance, "user_id", user_id), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:7079/savingaccounts",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // Assuming the response contains the new saving account ID
            String savingAccountId = responseEntity.getBody();
            String path = "redirect:/home?param=" + user_id;
            return path;
        } catch (Exception e) {
            return "404";
        }
    }

    /**
     * loan account
     */
    @GetMapping("/newLoan/{id}")
    public String newLoanAccountpage(@PathVariable int id, Model model) {
        try {
            model.addAttribute("id", id);
            return "newLoan";
        } catch (Exception e) {
            return "404";
        }
    }

    @PostMapping("/newloanaccount")
    public String saveLoanAccount(@RequestParam("initialAmount") BigDecimal balance, @RequestParam("userId") int user_id, @RequestParam("timePeriod") int time, Model model) {
        try {// Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the parameters and headers
            HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("balance", balance, "user_id", user_id, "time_period", time), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:7079/loanAccount",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // Assuming the response contains the new saving account ID
            String savingAccountId = responseEntity.getBody();
            String path = "redirect:/home?param=" + user_id;
            return path;
        } catch (Exception e) {
            return "404";
        }
    }

    /**
     * fixed account
     */
    @GetMapping("/newFixed/{id}")
    public String newFixedAccountpage(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("id", id);
            return "newFixed";
        } catch (Exception e) {
            return "404";
        }

    }

    @PostMapping("/newfixedaccount")
    public String saveFixedAccount(@RequestParam("initialAmount") BigDecimal balance, @RequestParam("userId") int user_id, @RequestParam("timePeriod") int time) {
        try {// Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the parameters and headers
            HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("balance", balance, "user_id", user_id, "time_period", time), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:7079/newfixedaccount",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // Assuming the response contains the new saving account ID
            String savingAccountId = responseEntity.getBody();
            String path = "redirect:/home?param=" + user_id;
            return path;
        } catch (Exception e) {
            return "404";
        }
    }

    /**
     * Fund transfer
     */
    @GetMapping("/tranfer")
    public String transferPage(Model model) {
        try {
            return "fundtransfer";

        } catch (Exception e) {
            return "404";
        }
    }


    @PostMapping("/transfer")
    public String transferfunds(@RequestParam("initialAmount") BigDecimal balance, @RequestParam("userId") int user_id, @RequestParam("timePeriod") int time) {
        try {// Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the parameters and headers
            HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("balance", balance, "user_id", user_id, "time_period", time), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:7079/transfer",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // Assuming the response contains the new saving account ID
            String savingAccountId = responseEntity.getBody();
            String path = "redirect:/home?param=" + user_id;
            return path;
        } catch (Exception e) {
            return "404";
        }
    }

    /**
     * trnsactions
     */
    @GetMapping("/transactions")
    public String transactionPage( Model model) {
        try {
            return "transactions";

        } catch (Exception e) {
            return "404";
        }
    }

    @PostMapping("/transactions")
//    public String transaction(@RequestParam("payamount")BigDecimal amount,  @RequestParam("userId")int user_id,@RequestParam("tranType")String tranType,@RequestParam("accountNumber")Integer account_number, Model model){
    public String processTransaction(@RequestParam("accountNumber") String account_number,
                                     @RequestParam("tranType") String tranType,
                                     @RequestParam("payamount") float amount,
                                     Model model) {
        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity with the parameters and headers
        HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("amount", amount, "tranType", tranType, "account_number", account_number), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:7079/transfer",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        // Assuming the response contains the new saving account ID
        String msg = responseEntity.getBody();
        model.addAttribute("msg", msg);
        return "/transactions";
    }

    /************  loan repay    ****/
    @GetMapping("/repay")
    public String repayPage(Model model){
        try {
            return "repay";

        } catch (Exception e){
            return "404";
        }
    }

    @PostMapping("/repay")
    public String repayLoan(@RequestParam("initialAmount") float balance,@RequestParam("accountNumber") String account_number, Model model){
        try {
            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the parameters and headers
            HttpEntity<Object> requestEntity = new HttpEntity<>(Map.of("amount",balance,  "account_number", account_number), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:7079/repay",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // Assuming the response contains the new saving account ID
            String msg = responseEntity.getBody();
            model.addAttribute("msg", msg);
            return "repay";


        } catch (Exception e){
            return "404";
        }
    }

}
