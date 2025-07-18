package com.t1homework.starter.receivingAndForming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Data
public class Command {
    @NotBlank
    @Size(max = 1000)
    String description;

    @NotNull
    CommandPriority priority;

    @NotBlank
    @Size(max = 100)
    String author;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime localDateTime;

    public Command(LocalDateTime localDateTime){
        this.localDateTime = LocalDateTime.now();
    }
}