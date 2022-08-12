package com.rddev.hrpayroll.services;

import com.rddev.hrpayroll.dto.Worker;
import com.rddev.hrpayroll.entities.Payment;
import com.rddev.hrpayroll.feignclients.WorkerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentService {

    private final WorkerFeignClient workerFeignClient;

    @LoadBalanced
    public Payment getPayment(Long workerId, Integer days) {
        Worker worker = workerFeignClient.getWorker(workerId).getBody();
        return new Payment(worker.name(), worker.dailyIncome(), days);
    }

}
