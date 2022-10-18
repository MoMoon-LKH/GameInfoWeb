package com.project.gameInfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocsController {


    @RequestMapping("/docs")
    public String docs(){

        return "/docs/api.html";
    }
}
