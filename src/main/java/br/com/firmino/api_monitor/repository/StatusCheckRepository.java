package br.com.firmino.api_monitor.repository;

import br.com.firmino.api_monitor.entity.StatusCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Esta interface é o Repositório, a camada responsável pela comunicação
 * com o banco de dados.
 * {@code @Repository} é uma anotação do Spring que marca a classe como um repositório,
 * tornando-a um componente gerenciado pelo Spring e habilitando o tratamento de exceções.
 * A herança de {@code JpaRepository<StatusCheck, Long>} é a parte mais poderosa.
 * Ela fornece, de forma automática, uma série de métodos prontos para o CRUD
 * (Create, Read, Update, Delete), como {@code save()}, {@code findById()}, {@code findAll()}, etc.
 * {@code StatusCheck} é a entidade que este repositório vai gerenciar.
 * {@code Long} é o tipo de dado da chave primária (ID) da entidade.
 * Não é necessário adicionar nenhum código aqui, pois o Spring Data JPA
 * gera a implementação em tempo de execução.
 */
@Repository
public interface StatusCheckRepository extends JpaRepository<StatusCheck, Long> {
}