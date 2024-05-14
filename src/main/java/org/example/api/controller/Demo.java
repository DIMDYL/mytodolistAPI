package org.example.api.controller;

import org.example.api.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Demo {
    @GetMapping("/1")
    public Result text(){
        return Result.success("1");
    }
}
