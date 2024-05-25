package ComandaXpress;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Codificacion {
    @AfterReturning(pointcut = "execution(* com.example.comandaxpress..*(..)) && @within(org.springframework.web.bind.annotation.RestController)", returning = "responseEntity")
    public void addCharsetHeader(Object responseEntity) {
        if (responseEntity instanceof ResponseEntity) {
            ResponseEntity<?> response = (ResponseEntity<?>) responseEntity;
            HttpHeaders headers = new HttpHeaders();
            headers.addAll(response.getHeaders());
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
            responseEntity = new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
        }
    }
}