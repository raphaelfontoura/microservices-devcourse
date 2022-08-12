package com.rddev.hrpayroll.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class WorkerLoadBalanceConfiguration {

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new AppServiceInstanceListSupplier("hr-worker");
    }

}

class AppServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;

    public AppServiceInstanceListSupplier(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays.asList(
                new DefaultServiceInstance(serviceId+"1", serviceId, "localhost", 8001, false),
                new DefaultServiceInstance(serviceId+"2", serviceId, "localhost", 8002, false)
        ));
    }
}
