package com.weyland.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WeylandWatchingYou {
    AuditMode auditMode() default AuditMode.CONSOLE;

    enum AuditMode {
        CONSOLE, KAFKA
    }
}
