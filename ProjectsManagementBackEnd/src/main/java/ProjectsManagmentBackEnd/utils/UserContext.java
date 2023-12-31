package ProjectsManagmentBackEnd.utils;


import ProjectsManagmentBackEnd.entity.user.User;
import ProjectsManagmentBackEnd.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserContext {



    static String userEmail() {
        return getUserInfos().getEmail();
    }


    static String userName() {
        return getUserInfos().getUsername();
    }

    static JwtUser getUserInfos() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (JwtUser) authentication.getPrincipal();
    }

    static User currentUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();


        JwtUser principal = (JwtUser) authentication.getPrincipal();

        return userRepository().findByUsernameOrEmail(principal.getUsername(), principal.getEmail()).get();
    }


    static UserRepository userRepository() {
        return
                ApplicationContextProvider.getContext().getBean(UserRepository.class);
    }

}
