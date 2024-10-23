package MSL.msl.ExpenseTracker.service;

import MSL.msl.ExpenseTracker.dto.Response;
import MSL.msl.ExpenseTracker.enums.ServiceCode;
import MSL.msl.ExpenseTracker.model.EmailVerificationToken;
import MSL.msl.ExpenseTracker.model.Users;
import MSL.msl.ExpenseTracker.repo.EmailVerificationRepo;
import MSL.msl.ExpenseTracker.repo.UserRepo;
import MSL.msl.ExpenseTracker.utility.ConfigReader;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Slf4j
public class AuthService {
    @Autowired
    UserRepo repo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    EmailVerificationRepo eRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    private DataSource dataSource;
    public void shutdownConnectionPool() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close(); // Close the connection pool
        }
    }



    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public ResponseEntity<Response> register(Users user) throws IOException{
//		Check for name
        Pattern patternName = Pattern.compile(ConfigReader.getNameRegexp());
        Matcher searchName = patternName.matcher(user.getName());
        boolean matchName = searchName.find();

//		check for email
        Pattern patternEmail = Pattern.compile(ConfigReader.getEmailRegexp());
        Matcher searchEmail = patternEmail.matcher(user.getEmail());
        boolean matchEmail = searchEmail.find();

//		check for password
        Pattern patternPassword = Pattern.compile(ConfigReader.getPasswordRegexp());
        Matcher searchPassword = patternPassword.matcher(user.getPassword());
        boolean matchPassword = searchPassword.find();

//		check for mobile no;
//        Pattern patternMobile = Pattern.compile(getMobileRegexp());
//        Matcher searchMobile = patternMobile.matcher(user.getMobile());
//        boolean matchMobileNo = searchMobile.find();

        System.out.println(user);
        if (null== user.getName()) {
            return ResponseEntity.badRequest().body(new Response(ServiceCode.SIGNUP04.getCode(),ServiceCode.SIGNUP04.getMessage(),null));
        } else if (!matchName) {
            return ResponseEntity.badRequest().body(new Response(ServiceCode.SIGNUP04.getCode(),ServiceCode.SIGNUP04.getMessage(),null));
        } else if (!matchEmail) {
            return ResponseEntity.badRequest().body(new Response(ServiceCode.SIGNUP09.getCode(),ServiceCode.SIGNUP09.getMessage(),null));
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new Response(ServiceCode.SIGNUP06.getCode(),ServiceCode.SIGNUP06.getMessage(),null));
        } else if (!matchPassword) {
            return ResponseEntity.badRequest().body(new Response(ServiceCode.SIGNUP10.getCode(),ServiceCode.SIGNUP10.getMessage(),null));
        }else if(repo.findByEmail(user.getEmail())!=null){
            return ResponseEntity.badRequest().body(new Response(ServiceCode.SIGNUP03.getCode(),ServiceCode.SIGNUP03.getMessage(),null));
        }else{
                user.setPassword(encoder.encode(user.getPassword()));
                user.setCreatedAt(LocalDateTime.now());
                user.setActive(false);
                repo.save(user);
                log.info("data {}",user);
                user.setCreatedBy(user.getUserId());
                repo.save(user);
//             *****************Email verification part****************************
                String token = UUID.randomUUID().toString();
                EmailVerificationToken verificationToken = new EmailVerificationToken();
                verificationToken.setToken(token);
                verificationToken.setUser(user);
                verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // token valid for 24 hours
                eRepo.save(verificationToken);
                emailService.sendVerificationEmail(user.getEmail(),token,user.getName());
                return ResponseEntity.ok().body(new Response(ServiceCode.SIGNUP01.getCode(),ServiceCode.SIGNUP01.getMessage(),null));
    	}
    }

    public ResponseEntity<Response> authenticateUser(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(new Response(ServiceCode.LOGIN01.getCode(),ServiceCode.LOGIN01.getMessage(),jwtService.generateToken(user.getUsername())));
        }
        return ResponseEntity.ok(new Response(ServiceCode.LOGIN02.getCode(),ServiceCode.LOGIN02.getMessage(),null));
    }

    public ResponseEntity<Response> generateOtp(Users user){
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // token valid for 24 hours
        eRepo.save(verificationToken);
        emailService.sendForgetPasswordEmail(user.getEmail(),token,user.getName());
        return ResponseEntity.ok().body(new Response(ServiceCode.VER02.getCode(),ServiceCode.VER02.getMessage(),null));
    }

    public ResponseEntity<Response> resetPassword(Users user, String newPassword) throws IOException {
        Pattern patternPassword = Pattern.compile(ConfigReader.getPasswordRegexp());
        Matcher searchPassword = patternPassword.matcher(newPassword);
        boolean matchPassword = searchPassword.find();
        if(matchPassword){
            user.setPassword(encoder.encode(newPassword));
            repo.save(user);
            return ResponseEntity.ok().body(new Response(ServiceCode.ETA01.getCode(),ServiceCode.ETA01.getMessage(),null));
        }
        return ResponseEntity.ok().body(new Response(ServiceCode.SIGNUP10.getCode(),ServiceCode.SIGNUP10.getMessage(),null));

    }
}
