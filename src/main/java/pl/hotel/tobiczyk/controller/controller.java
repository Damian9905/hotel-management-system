package pl.hotel.tobiczyk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class controller {

    @GetMapping(path = "/")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello! ");
    }
}
