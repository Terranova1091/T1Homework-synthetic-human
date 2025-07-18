package com.t1homework.starter.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WeylandWatchingYou {
    AuditMode mode() default AuditMode.CONSOLE;

    String topic() default "weyland-audit-log";
}
