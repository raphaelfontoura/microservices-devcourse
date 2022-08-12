package com.rddev.hrpayroll.services;

import com.rddev.hrpayroll.dto.Worker;
import com.rddev.hrpayroll.entities.Payment;
import com.rddev.hrpayroll.feignclients.WorkerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentService {

    private final WorkerFeignClient workerFeignClient;

    public Payment getPayment(Long workerId, Integer days) {
        Worker worker = workerFeignClient.getWorker(workerId).getBody();
        return new Payment(worker.name(), worker.dailyIncome(), days);
    }

}
