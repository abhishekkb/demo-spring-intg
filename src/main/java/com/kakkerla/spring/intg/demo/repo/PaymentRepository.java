package com.kakkerla.spring.intg.demo.repo;

import com.kakkerla.spring.intg.demo.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

}