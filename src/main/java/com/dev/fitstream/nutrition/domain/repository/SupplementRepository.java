package com.dev.fitstream.nutrition.domain.repository;

import com.dev.fitstream.nutrition.domain.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SupplementRepository extends JpaRepository<Supplement, UUID> {
}
