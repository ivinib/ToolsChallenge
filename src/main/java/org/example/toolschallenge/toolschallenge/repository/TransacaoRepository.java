package org.example.toolschallenge.toolschallenge.repository;

import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository  extends JpaRepository<Transacao, Long> {
}
