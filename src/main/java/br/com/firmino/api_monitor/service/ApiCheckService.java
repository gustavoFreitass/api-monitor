package br.com.firmino.api_monitor.service;

import br.com.firmino.api_monitor.api.MonitoredApi;
import br.com.firmino.api_monitor.repository.MonitoredApiRepository;
import br.com.firmino.api_monitor.entity.StatusCheck;
import br.com.firmino.api_monitor.repository.StatusCheckRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Este serviço é o "robô" que roda em segundo plano.
 * Sua única responsabilidade é verificar periodicamente o status
 * de todos os serviços cadastrados no banco de dados.
 */
@Service
@RequiredArgsConstructor
public class ApiCheckService {

    // --- Campos da Classe ---
    private static final Logger log =  LoggerFactory.getLogger(ApiCheckService.class);

    private final StatusCheckRepository statusCheckRepository;
    private final MonitoredApiRepository monitoredApiRepository;
    private final RestTemplate restTemplate;



    /**
     * Este método agendado é o gatilho principal do monitor.
     * Ele busca todos os serviços do banco e dispara a verificação para cada um.
     */
    @Scheduled(fixedRate = 60000) // Roda a cada 1 minuto
    public void checkAllApis() {
        log.info("INICIANDO VERIFICAÇÃO AGENDADA...");

        List<MonitoredApi> allServices = monitoredApiRepository.findAll();
        log.info(">>> {} serviços a serem verificados.", allServices.size());

        for (MonitoredApi service : allServices) {
            checkAndSaveApiStatus(service);
        }
        log.info(">>> VERIFICAÇÃO AGENDADA CONCLUÍDA.");
    }

    /**
     * Verifica o status de um único serviço e salva o resultado.
     * @param service O objeto MonitoredApi a ser verificado.
     */
    public void checkAndSaveApiStatus(MonitoredApi service) {
        StatusCheck newStatus = new StatusCheck();
        newStatus.setMonitoredApi(service);
        newStatus.setTimestamp(LocalDateTime.now());
        String url = service.getUrl();
        try {
            long startTime = System.currentTimeMillis();

            ResponseEntity<String> response = restTemplate.getForEntity(service.getUrl(), String.class);

            long endTime = System.currentTimeMillis();
            newStatus.setStatusCode(response.getStatusCode().value());
            newStatus.setResponseTimeMillis(endTime-startTime);

            log.info("  - SUCESSO: {} ({}) - Status: {} ({}ms)", service.getName(), service.getUrl(), newStatus.getStatusCode(), newStatus.getResponseTimeMillis());

        } catch (Exception e) {
            log.error(" - ERRO ao verificar {}: {}", service.getName(), e.getMessage());
            newStatus.setStatusCode(0);
            newStatus.setResponseTimeMillis(0L);
        }
        statusCheckRepository.save(newStatus);
    }
}