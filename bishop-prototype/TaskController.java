package com.REST_API.bishop_prototype.controller;


import com.weyland.starter.DTO.CommandDTO;
import com.weyland.starter.annotation.WeylandWatchingYou;
import com.weyland.starter.service.CommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final CommandService commandService;

    @PostMapping
    @WeylandWatchingYou(auditMode = WeylandWatchingYou.AuditMode.KAFKA)
    public ResponseEntity<String> submitTask(@Valid @RequestBody CommandDTO task) {
        commandService.processCommand(task);
        return ResponseEntity.ok("Задача добавлена в очередь");
    }
}