package com.t1homework.starter.receivingAndForming.processing;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.t1homework.starter.receivingAndForming.processing.exception.QueueOverflowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommandExceptionHandler {
    public CommandExceptionHandler() {
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, (fieldError) -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Некорректное значение"));
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Ошибка валидации данных");
        response.put("details", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({QueueOverflowException.class})
    public ResponseEntity<Map<String, Object>> handleQueueOverflowException(QueueOverflowException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
        response.put("error", "Ошибка выполнения команды");
        response.put("message", ex.getMessage());
        response.put("details", "Очередь команд переполнена. Попробуйте позже");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Ошибка разбора JSON");
        response.put("message", "Невозможно разобрать входящий JSON");
        response.put("details", "Проверьте формат и допустимые значения (например, приоритет должен быть: COMMON или CRITICAL)");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
