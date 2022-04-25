package com.calcoa.pricing.operations;

import com.calcoa.pricing.dao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
