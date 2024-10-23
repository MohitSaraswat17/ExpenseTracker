package MSL.msl.ExpenseTracker.controller;

import MSL.msl.ExpenseTracker.dto.EmailRequest;
import MSL.msl.ExpenseTracker.dto.ResetPassword;
import MSL.msl.ExpenseTracker.model.EmailVerificationToken;
import MSL.msl.ExpenseTracker.repo.EmailVerificationRepo;
import MSL.msl.ExpenseTracker.repo.UserRepo;
import MSL.msl.ExpenseTracker.enums.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import MSL.msl.ExpenseTracker.dto.Response;
import MSL.msl.ExpenseTracker.model.Users;
import MSL.msl.ExpenseTracker.service.AuthService;
import java.io.IOException;
import java.time.LocalDateTime;



@RestController
@CrossOrigin
@RequestMapping("/eta")
@Slf4j
public class AuthController{
    @Autowired
    AuthService service;

    @Autowired
    EmailVerificationRepo eRepo;

    @Autowired
    UserRepo repo;
    
    @PostMapping("/signup")
    public ResponseEntity<Response> register(@RequestBody Users user) throws IOException{
        log.info("Trying to register user {}",user);
        return service.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody Users user) {
        log.info("Trying to login");
        return service.authenticateUser(user);
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestBody Response responseToken) {

        EmailVerificationToken verificationToken = eRepo.findByToken(responseToken.getJwtToken());
        log.info("verification token {}",verificationToken);

        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Invalid or expired verification token {}",verificationToken);
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        Users user = verificationToken.getUser();
        user.setActive(true);
        repo.save(user);
        eRepo.delete(verificationToken);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Response> forgetPassword(@RequestBody EmailRequest emailRequest) {
        log.info("Trying to forget your password {}",emailRequest.getEmail());
        String email = emailRequest.getEmail();
        Users user = repo.findByEmail(email);
        log.info("finding uer details in database{}",user);
        if (user != null) {
            // Check if user exists
            return service.generateOtp(user);
        }else{
            log.warn("Invalid request! you are not registered{} ",emailRequest.getEmail());
            return ResponseEntity.badRequest().body(new Response(ServiceCode.VER03.getCode(),ServiceCode.VER03.getMessage(),null));
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPassword resetPassword) throws IOException {
        log.info("Trying to reset password");
        EmailVerificationToken verificationToken = eRepo.findByToken(resetPassword.getToken());
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("invalid or expired token!! {}",verificationToken);
            return ResponseEntity.ok().body(new Response(ServiceCode.ETA02.getCode(),ServiceCode.ETA02.getMessage(),null));
        }
        Users user = verificationToken.getUser();
        log.info("Finding user details {}",user);
        eRepo.delete(verificationToken);
        return service.resetPassword(user,resetPassword.getNewPassword());
    }
}
