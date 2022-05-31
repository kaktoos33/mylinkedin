package ir.manoosheh.mylinkedin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
//@RequestMapping("/status")
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(HttpServletRequest request) {
        String origin = URI.create(request.getRequestURL().toString()).getHost();
        return ResponseEntity.ok("OK : " + origin);
    }
}
