package com.rddev.hrpayroll.services;

import com.rddev.hrpayroll.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public Payment getPayment(Long workerId, Integer days) {
        return new Payment("John Doe", 200.0, days);
    }
}
