package com.msa.product.util.idempotency.repository;

import com.msa.product.util.idempotency.model.EventProceeded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventProceededRepository extends JpaRepository<EventProceeded, Long> {
}
