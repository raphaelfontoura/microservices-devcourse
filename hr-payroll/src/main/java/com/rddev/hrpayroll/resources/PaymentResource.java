package com.rddev.hrpayroll.resources;

import com.rddev.hrpayroll.entities.Payment;
import com.rddev.hrpayroll.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentResource {

    private final PaymentService service;


    @Retryable(value = {RemoteAccessException.class}, maxAttempts = 3)
    @GetMapping("/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        return ResponseEntity.ok(service.getPayment(workerId, days));
    }

    @Recover
    public ResponseEntity<Payment> getFallbackPayment(Long workerId, Integer days) {
        Payment payment = new Payment("Worker not found", 0.0, days);
        return ResponseEntity.ok(payment);
    }
}
