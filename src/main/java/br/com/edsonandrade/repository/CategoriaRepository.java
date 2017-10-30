package br.com.edsonandrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edsonandrade.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
