package com.weyland.starter.annotation.aspect;


import com.weyland.starter.annotation.WeylandWatchingYou;
import com.weyland.starter.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditService auditService;

    @Around("@annotation(weylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, WeylandWatchingYou weylandWatchingYou) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        Object result = null;
        Throwable error = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            error = t;
            throw t; // повторно кидаем исключение
        } finally {
            try {
                auditService.audit(weylandWatchingYou.auditMode(), methodName, args, result, error);
            } catch (Exception auditEx) {
                log.warn("Ошибка при выполнении аудита: {}", auditEx.getMessage(), auditEx);
            }
        }
    }
}
