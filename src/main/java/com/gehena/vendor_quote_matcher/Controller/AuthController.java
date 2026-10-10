
package com.gehena.vendor_quote_matcher.Controller;

import com.gehena.vendor_quote_matcher.Service.AuthService;
import com.gehena.vendor_quote_matcher.dto.LoginRequest;
import com.gehena.vendor_quote_matcher.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        return authService.register(name, email, password);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}