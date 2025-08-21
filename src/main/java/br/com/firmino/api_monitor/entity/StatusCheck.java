package br.com.firmino.api_monitor.entity;

import br.com.firmino.api_monitor.api.MonitoredApi;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Esta é a nossa Entidade, ou seja, a classe que representa a tabela no banco de dados.
 * <p>
 * A anotação {@code @Data} do Lombok gera automaticamente todos os métodos essenciais, como
 * getters, setters, {@code toString()}, {@code equals()} e {@code hashCode()},
 * evitando a necessidade de escrevê-los manualmente.
 * <p>
 * A anotação {@code @Entity} do JPA (Java Persistence API) informa ao Spring que esta
 * classe corresponde a uma tabela em nosso banco de dados.
 */
@Data
@Entity
public class StatusCheck {

    /**
     * Este é o campo de chave primária da tabela.
     * <p>
     * {@code @Id} define que este atributo é a chave primária.
     * {@code @GeneratedValue} configura a estratégia de geração automática de valores
     * para o ID. {@code GenerationType.IDENTITY} permite que o próprio banco de dados
     * cuide de gerar um valor único e crescente para cada novo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código de status da resposta HTTP (ex: 200 para OK, 404 para Não Encontrado).
     */
    @Column(nullable = false)
    private int statusCode;

    /**
     * Data e hora exata em que a verificação de status foi realizada.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Tempo de resposta da API em milissegundos.
     */
    private Long responseTimeMillis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitored_api_id", nullable = false)
    private MonitoredApi monitoredApi;
}