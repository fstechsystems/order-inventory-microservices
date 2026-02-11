package com.company.sales.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.sales.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}