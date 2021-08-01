package com.dj.server.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * LB 서버 상태 확인 Controller
 *
 */

@Slf4j
@RestController
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
