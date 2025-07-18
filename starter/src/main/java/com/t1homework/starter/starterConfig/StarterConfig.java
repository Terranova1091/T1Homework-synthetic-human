package com.t1homework.starter.starterConfig;

import com.t1homework.starter.receivingAndForming.processing.CommandExecutor;
import com.t1homework.starter.receivingAndForming.processing.CommandQueueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StarterConfig {


    @Bean
    public CommandExecutor commandExecutor(CommandQueueService queueService) {
        return new CommandExecutor(queueService);
    }
}