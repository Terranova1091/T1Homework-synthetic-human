package com.t1homework.starter.audit;


import java.lang.reflect.Method;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuditAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(com.t1homework.starter.audit.WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        WeylandWatchingYou annotation = method.getAnnotation(WeylandWatchingYou.class);
        AuditMode mode = annotation.mode();
        String topic = annotation.topic();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        String message = "[AUDIT]===========================================" +
                "\n Метод: " + methodName +
                ",\n Тело: " + Arrays.toString(args) +
                ",\n Результат вызова: " + result +
                "\n[AUDIT]===========================================";
        if (mode == AuditMode.KAFKA && this.kafkaTemplate != null) {
            this.kafkaTemplate.send(topic, message);
        } else {
            System.out.println(message);
        }

        return result;
    }
}
