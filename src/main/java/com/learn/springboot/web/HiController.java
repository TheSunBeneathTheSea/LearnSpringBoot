package com.learn.springboot.web;

import com.learn.springboot.web.dto.HiResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }

    @GetMapping("/hi/dto")
    public HiResponseDto hiDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HiResponseDto(name, amount);
    }
}
