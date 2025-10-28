package org.example.toolschallenge.toolschallenge.repository;

import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository  extends JpaRepository<Transacao, Long> {
}
