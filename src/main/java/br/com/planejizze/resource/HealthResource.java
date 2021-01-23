package br.com.planejizze.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/health")
public class HealthResource {

    @GetMapping
    public ResponseEntity health() {
        return ResponseEntity.ok("Api funcionando!");
    }
}
