package com.t1homework.starter.receivingAndForming.processing;


import com.t1homework.starter.receivingAndForming.model.Command;
import com.t1homework.starter.receivingAndForming.model.CommandPriority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandExecutor {

    private final CommandQueueService queueService;

    public void execute(Command command) {
        Runnable task = () -> {
            log.info(
                    "Выполнение команды: [{}] от {} (Приоритет: {}).",
                    command.getDescription(), command.getAuthor(), command.getPriority()
            );

            queueService.incrementAuthorCounter(command.getAuthor());
        };

        if (command.getPriority() == CommandPriority.CRITICAL) {
            task.run();
            log.info("CRITICAL Задача выполнена ");
        } else {
            queueService.addTask(task);
        }

    }
}
