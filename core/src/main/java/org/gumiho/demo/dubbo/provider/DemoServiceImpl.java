package org.gumiho.demo.dubbo.provider;

import org.gumiho.demo.dubbo.DemoService;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello" + name;
    }
}
