package br.com.firmino.api_monitor.service;

import br.com.firmino.api_monitor.entity.StatusCheck;
import br.com.firmino.api_monitor.repository.StatusCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Esta classe é um Serviço ({@code @Service}), responsável por conter a lógica de negócio da aplicação.
 * Sua principal função é verificar o status de APIs (Endpoints) e salvar os resultados no banco de dados.
 * Ela também gerencia o agendamento de tarefas para realizar essas verificações periodicamente.
 */
@Service
public class ApiCheckService {

    /**
     * Instância de {@link HttpClient} usada para fazer as requisições HTTP.
     * {@code HttpClient} é uma classe moderna e eficiente para enviar requisições e receber respostas.
     */
    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Repositório ({@link StatusCheckRepository}) para interagir com o banco de dados.
     * A anotação {@code @Autowired} injeta automaticamente uma instância do repositório,
     * permitindo que a gente salve e recupere os dados de status das APIs.
     */
    @Autowired
    private StatusCheckRepository repository;

    /**
     * Uma lista de URLs para serem verificadas.
     * A anotação {@code @Value} lê a propriedade "monitor.urls" de um arquivo de configuração
     * (como application.properties) e preenche essa lista automaticamente.
     */
    @Value("${monitor.urls}")
    private List<String> urlsParaVerificar;

    /**
     * Este método verifica o status de uma única URL.
     * Ele envia uma requisição HTTP GET, mede o tempo de resposta e salva os detalhes no banco de dados.
     *
     * @param url A URL da API a ser verificada.
     */
    public void checkApiStatus(String url) {
        try {
            long startTime = System.currentTimeMillis();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            long endtime = System.currentTimeMillis();
            long duration = endtime - startTime;
            int statusCode = response.statusCode();
            System.out.println("Status da API: " + url + ": " + statusCode);

            StatusCheck status = new StatusCheck();
            status.setUrl(url);
            status.setStatusCode(statusCode);
            status.setTimestamp(LocalDateTime.now());
            status.setResponseTimeMillis(duration);
            repository.save(status);
        } catch (Exception e) {
            System.out.println("Erro ao verificar a API " + url + ": " + e.getMessage());
        }
    }

    /**
     * Este método é agendado para rodar periodicamente e verificar todas as URLs da lista.
     *
     * A anotação {@code @Scheduled} define que o método deve ser executado a cada 60000 milissegundos,
     * ou seja, a cada 1 minuto.
     */
    @Scheduled(fixedRate = 60000)
    public void verificarApisAgendadas() {
        System.out.println(">>EXECUTANDO VERIFICAÇÃO AGENDADA...");

        for (String url : urlsParaVerificar) {
            checkApiStatus(url);
        }
        System.out.println(">>VERIFICAÇÃO AGENDADA CONCLUÍDA");
    }

    /**
     * Este método busca e retorna todos os registros de status do banco de dados.
     * Ele delega a operação ao repositório, que já tem o método {@code findAll()} pronto.
     *
     * @return Uma lista de todos os objetos {@link StatusCheck} salvos.
     */
    public List<StatusCheck> buscarTodosOsStatus() {
        return repository.findAll();
    }
}