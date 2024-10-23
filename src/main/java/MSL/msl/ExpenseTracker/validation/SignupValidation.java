package MSL.msl.ExpenseTracker.validation;

import MSL.msl.ExpenseTracker.model.Users;
import MSL.msl.ExpenseTracker.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MSL.msl.ExpenseTracker.utility.ConfigReader.getNameRegexp;
import static MSL.msl.ExpenseTracker.utility.ConfigReader.getEmailRegexp;
import static MSL.msl.ExpenseTracker.utility.ConfigReader.getMobileRegexp;
import static MSL.msl.ExpenseTracker.utility.ConfigReader.getPasswordRegexp;

import lombok.Data;

@Component
@Data
public class SignupValidation {
	@Autowired
	UserRepo repo;

	public ResponseEntity<String> validateUserDetails(Users user) throws IOException {
//		ServiceCode svc = ServiceCode.SIGNUP01;
		
//		Check for name
		Pattern patternName = Pattern.compile(getNameRegexp());
		Matcher searchName = patternName.matcher(user.getName());
		boolean matchName = searchName.find();

//		check for email
		Pattern patternEmail = Pattern.compile(getEmailRegexp());
		Matcher searchEmail = patternEmail.matcher(user.getEmail());
		boolean matchEmail = searchEmail.find();

//		check for password
		Pattern patternPassword = Pattern.compile(getPasswordRegexp());
		Matcher searchPassword = patternPassword.matcher(user.getPassword());
		boolean matchPassword = searchPassword.find();

//		check for mobile no;
		Pattern patternMobile = Pattern.compile(getMobileRegexp());
		Matcher searchMobile = patternMobile.matcher(user.getMobile());
		boolean matchMobileNo = searchMobile.find();

		System.out.println(user);
		 if (null== user.getName()) {
			return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (!matchName) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (!matchEmail) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (!user.getPassword().equals(user.getConfirmPassword())) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (!matchPassword) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (!matchMobileNo) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (repo.findByEmail(user.getEmail())!=null) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} else if (repo.findByMobile(user.getMobile())!=null) {
			 return ResponseEntity.badRequest().body("Name can not be Null");
		} 
		return ResponseEntity.ok().body("Registration Successfully");
	}

}
