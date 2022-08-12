package com.rddev.hrpayroll.services;

import com.rddev.hrpayroll.dto.Worker;
import com.rddev.hrpayroll.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentService {
    
    @Value("${hr-worker.url}")
    private String workerUrl;
    
    private final RestTemplate restTemplate;

    public Payment getPayment(Long workerId, Integer days) {
        Worker worker = getWorkerById(workerId);
        return new Payment(worker.name(), worker.dailyIncome(), days);
    }

    private Worker getWorkerById(Long workerId) {
        Map<String, String> uriVariables = Map.of("id", workerId.toString());
        return restTemplate.getForObject(workerUrl + "/workers/{id}", Worker.class, uriVariables);
    }
}
