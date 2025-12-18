package com.project.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("auditAwareImpl")
public class AuditSupportImpl implements AuditorAware<String> {
    public Optional<String> getCurrentAuditor(){
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
