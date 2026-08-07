package io.github.dougbt.banking.infrastructure.repository;

import io.github.dougbt.banking.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNumeroConta(Integer numeroConta);

    boolean existsByNumeroConta(Integer numeroConta);
}
