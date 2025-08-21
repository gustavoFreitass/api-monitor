package br.com.firmino.api_monitor.service;

import br.com.firmino.api_monitor.api.MonitoredApi;
import br.com.firmino.api_monitor.api.MonitoredApiRepository;
import br.com.firmino.api_monitor.entity.StatusCheck;
import br.com.firmino.api_monitor.repository.StatusCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Este serviço é o "robô" que roda em segundo plano.
 * Sua única responsabilidade é verificar periodicamente o status
 * de todos os serviços cadastrados no banco de dados.
 */
@Service
public class ApiCheckService {

    // --- Campos da Classe ---
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Autowired
    private StatusCheckRepository statusCheckRepository;

    @Autowired
    private MonitoredApiRepository monitoredApiRepository;


    /**
     * Este método agendado é o gatilho principal do monitor.
     * Ele busca todos os serviços do banco e dispara a verificação para cada um.
     */
    @Scheduled(fixedRate = 60000) // Roda a cada 1 minuto
    public void checkScheduledApis() {
        System.out.println(">>> RUNNING SCHEDULED CHECK... - " + LocalDateTime.now());

        List<MonitoredApi> allServices = monitoredApiRepository.findAll();
        System.out.println(">>> " + allServices.size() + " services to be checked.");

        for (MonitoredApi service : allServices) {
            checkApiStatus(service);
        }
        System.out.println(">>> SCHEDULED CHECK COMPLETED.");
    }

    /**
     * Verifica o status de um único serviço e salva o resultado.
     * @param service O objeto MonitoredApi a ser verificado.
     */
    public void checkApiStatus(MonitoredApi service) {
        String url = service.getUrl();
        try {
            long startTime = System.currentTimeMillis();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            int statusCode = response.statusCode();
            System.out.println("  - Status: " + service.getName() + " (" + url + "): " + statusCode + " (" + duration + "ms)");

            StatusCheck newStatus = new StatusCheck();
            newStatus.setStatusCode(statusCode);
            newStatus.setTimestamp(LocalDateTime.now());
            newStatus.setResponseTimeMillis(duration);
            newStatus.setMonitoredApi(service);

            statusCheckRepository.save(newStatus);

        } catch (Exception e) {
            System.err.println("  - Error checking " + service.getName() + " (" + url + "): " + e.getMessage());
        }
    }

    // O método antigo getAllStatusChecks() foi removido.
}