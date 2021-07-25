package com.dj.server.api.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        log.info("상태: success");
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
