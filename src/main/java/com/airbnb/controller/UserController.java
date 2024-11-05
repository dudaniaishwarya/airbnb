package com.airbnb.controller;
import com.airbnb.dto.LoginDto;
import com.airbnb.dto.Tokenresponse;
import com.airbnb.dto.entity.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto){
             PropertyUser propertyUser =  userService.addUser(propertyUserDto);
             if(propertyUser!=null){
             return new ResponseEntity<>("Registration is successful", HttpStatus.CREATED);
    }
             return new ResponseEntity<>("Something went wrong ",HttpStatus.INTERNAL_SERVER_ERROR);


    }
    @PostMapping("/login")
    public <TokenResponse> ResponseEntity<?> login(@RequestBody  LoginDto loginDto){
        String token = userService.verifylogin(loginDto);
        if(token!=null){
            Tokenresponse tokenResponse = new Tokenresponse();
            tokenResponse.setToken(token);

            return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>( "in valid crdentials",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){
        return user;

    }



}
