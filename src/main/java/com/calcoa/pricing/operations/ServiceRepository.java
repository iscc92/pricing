package com.calcoa.pricing.operations;

import com.calcoa.pricing.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
public interface ServiceRepository extends JpaRepository<ServiceType, String> {
}
