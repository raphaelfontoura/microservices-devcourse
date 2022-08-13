package com.rddev.hrworker.resources;

import com.rddev.hrworker.entities.Worker;
import com.rddev.hrworker.repositories.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkerResource {

    private final Environment environment;

    private final WorkerRepository repository;

    @Value("${test.config}")
    private String configValue;

    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id) {

        log.info("PORT : {}", environment.getProperty("local.server.port"));

        return ResponseEntity.ok(repository.findById(id).get());
    }

    @GetMapping("/config")
    public ResponseEntity<String> getConfig() {
        return ResponseEntity.ok(configValue);
    }
}
