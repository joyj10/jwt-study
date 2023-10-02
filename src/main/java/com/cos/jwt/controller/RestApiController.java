package com.cos.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestApiController
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@RestController
public class RestApiController {

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>token</h1>";
    }


}
