package MSL.msl.ExpenseTracker.service;

import MSL.msl.ExpenseTracker.enums.ServiceCode;
import MSL.msl.ExpenseTracker.model.UserPrincipal;
import MSL.msl.ExpenseTracker.model.Users;
import MSL.msl.ExpenseTracker.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username {}",username);
        Users user=null;
        if(userRepo.findByMobile(username)!=null){
            user=userRepo.findByMobile(username);
            user.setUsername(username);
        }
        else if(userRepo.findByEmail(username)!=null){
            user=userRepo.findByEmail(username);
            user.setUsername(username);
        }else{
            log.warn("{} {}", ServiceCode.LOGIN02,ServiceCode.LOGIN02.getMessage());
            throw new UsernameNotFoundException(ServiceCode.LOGIN02.getMessage());
        }
        return new UserPrincipal(user);
    }
}