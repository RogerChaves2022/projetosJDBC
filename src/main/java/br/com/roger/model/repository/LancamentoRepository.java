package br.com.roger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.roger.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
