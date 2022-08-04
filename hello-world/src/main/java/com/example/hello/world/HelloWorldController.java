package com.example.hello.world;

import com.example.platform.library.rest.controller.AbstractRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController extends AbstractRestController {
  @GetMapping("/hello")
  public String hello() {
    return "Hello";
  }
}
