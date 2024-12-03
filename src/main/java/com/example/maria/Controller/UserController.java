package com.example.maria.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.entity.Loan;
import com.example.maria.entity.PersonalInformation;
import com.example.maria.entity.User;
import com.example.maria.repository.PersonalInformationRepository;
import com.example.maria.service.LoanService;
import com.example.maria.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private PersonalInformationRepository personalInformationRepository;
    @Autowired
    private UserService userService;
    
    @PostMapping("/loan")
    public ResponseEntity<String> applyForLoan(@RequestBody Loan loan) {
        try {
            loanService.applyLoan(loan); // Call service to process loan
            return ResponseEntity.ok("Loan submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting loan.");
        }
    }
    // Endpoint to get a user's transaction history
    @GetMapping("/loans/{userId}")
    public ResponseEntity<List<Loan>> getLoanHistory(@PathVariable Long userId) {
        List<Loan> loans = loanService.getLoanHistory(userId);
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }
     @GetMapping("/{userId}/about")
    public ResponseEntity<PersonalInformation> getUserInfo(@PathVariable Long userId) {
        Optional<PersonalInformation> personalInfo = personalInformationRepository.findByUserId(userId);
        
        if (personalInfo.isPresent()) {
            return ResponseEntity.ok(personalInfo.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/{username}/user")
    public ResponseEntity<User> getUserInfoByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found if user is not found
        }
    }

}
