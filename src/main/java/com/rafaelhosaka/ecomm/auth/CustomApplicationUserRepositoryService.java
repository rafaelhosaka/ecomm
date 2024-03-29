package com.rafaelhosaka.ecomm.auth;

import com.rafaelhosaka.ecomm.account.UserAccount;
import com.rafaelhosaka.ecomm.account.UserAccountRepository;
import com.rafaelhosaka.ecomm.exception.PasswordNotCorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("custom")
public class CustomApplicationUserRepositoryService implements ApplicationUserRepository{

    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public CustomApplicationUserRepositoryService(PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userAccountRepository = userAccountRepository;
    }

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return Optional.empty();
    }

    public ApplicationUser getApplicationUserByUsername(String username){
        UserAccount userAccount= userAccountRepository.findByUsername(username);
        return new ApplicationUser(userAccount);
    }

    public void createApplicationUser(UserDetails userDetails){
        if(userDetails instanceof ApplicationUser) {
            ApplicationUser applicationUser = (ApplicationUser)userDetails;
            applicationUser.getUserAccount().setPassword(passwordEncoder.encode(applicationUser.getPassword()));
            userAccountRepository.save((UserAccount) applicationUser.getUserAccount());
        }
    }

    @Override
    public void updateApplicationUserPassword(UserAccount userAccount, String currentPassword, String newPassword) throws PasswordNotCorrectException {
        if(!passwordEncoder.matches(currentPassword,userAccount.getPassword())){
            throw new PasswordNotCorrectException("208","Password not correct, please try again");
        }
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(userAccount);
    }
}
