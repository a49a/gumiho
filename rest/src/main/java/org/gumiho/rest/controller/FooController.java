package org.gumiho.rest.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @GetMapping("/foo/{id}")
    public String getFoo(@PathVariable String id) {
        return JSON.toJSONString(id);
    }
}
