package com.lcg.sample.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditingConfig implements AuditorAware<Long> {
    public Optional<Long> getCurrentAuditor() {
//        security获取当前会话者
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        Object principal = ctx.getAuthentication().getPrincipal();
        return Optional.of(125241L);
    }
}
