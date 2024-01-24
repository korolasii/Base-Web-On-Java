package com.baseWeb.baseWeb;

import org.springframework.web.bind.annotation.GetMapping;

public class Controller {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
