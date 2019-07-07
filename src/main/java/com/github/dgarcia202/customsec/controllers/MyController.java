package com.github.dgarcia202.customsec.controllers;

import com.github.dgarcia202.customsec.dto.DataDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("api/data")
    public DataDto getData() {
        return new DataDto("this is a protected resource!!");
    }

    @GetMapping("open-data")
    public DataDto getOpenData() {
        return new DataDto("this is a free access resource");
    }
}
