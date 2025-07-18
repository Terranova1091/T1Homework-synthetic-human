package com.t1homework.bishop_prototype.testController;

import com.t1homework.starter.audit.AuditMode;
import com.t1homework.starter.audit.WeylandWatchingYou;
import com.t1homework.starter.receivingAndForming.model.Command;
import com.t1homework.starter.receivingAndForming.processing.CommandExecutor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/commands"})
public class TestController {
    private final CommandExecutor executor;

    @PostMapping
    @WeylandWatchingYou(mode = AuditMode.CONSOLE, topic = "synthetic-audit")
    public ResponseEntity<String> receiveCommand(@RequestBody @Valid Command command) {
        this.executor.execute(command);
        return ResponseEntity.ok("Компанда принята!");
    }
}