package br.com.firmino.api_monitor.service;

import br.com.firmino.api_monitor.api.MonitoredApi;
import br.com.firmino.api_monitor.dto.ApiServiceStatusDTO;
import br.com.firmino.api_monitor.entity.StatusCheck;
import br.com.firmino.api_monitor.repository.MonitoredApiRepository;
import br.com.firmino.api_monitor.repository.StatusCheckRepository;
import br.com.firmino.api_monitor.entity.User;
import br.com.firmino.api_monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoredApiService {

    private final MonitoredApiRepository monitoredApiRepository;
    private final StatusCheckRepository statusCheckRepository;
    private final UserRepository userRepository;


    public List<ApiServiceStatusDTO>findApisByUsername(String identifier){
        User user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o identificador: " + identifier));
        return getServicesForUser(user);
    }
    public List<ApiServiceStatusDTO> getPublicServices() {
        List<MonitoredApi> services = monitoredApiRepository.findByIsPublicTrue();
        return services.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ApiServiceStatusDTO> getServicesForUser(User user) {
        List<MonitoredApi> services = monitoredApiRepository.findByUser(user);
        return services.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private ApiServiceStatusDTO convertToDto(MonitoredApi api) {
        Optional<StatusCheck> latestStatusOpt = statusCheckRepository.findTopByMonitoredApiOrderByTimestampDesc(api);

        ApiServiceStatusDTO dto = new ApiServiceStatusDTO();
        dto.setId(api.getId());
        dto.setName(api.getName());
        dto.setUrl(api.getUrl());
        dto.setDescription(api.getDescription());
        dto.setServiceType(api.getServiceType().toString());

        if (latestStatusOpt.isPresent()) {
            StatusCheck latestStatus = latestStatusOpt.get();
            dto.setStatusCode(latestStatus.getStatusCode());
            dto.setResponseTime(latestStatus.getResponseTimeMillis());
            dto.setLastCheck(latestStatus.getTimestamp());
            if (latestStatus.getStatusCode() >= 200 && latestStatus.getStatusCode() < 400) {
                dto.setStatus(latestStatus.getResponseTimeMillis() < 1500 ? "Operational" : "Slow Response");
            } else {
                dto.setStatus("Outage");
            }
        } else {
            dto.setStatus("Outage");
            dto.setStatusCode(0);
            dto.setResponseTime(0);
        }
        return dto;
    }

    public MonitoredApi createService(MonitoredApi apiToCreate, User owner){
        apiToCreate.setUser(owner);
        return monitoredApiRepository.save(apiToCreate);
    }

    public void deleteService(Long serviceId, User user){
        MonitoredApi service = monitoredApiRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + serviceId));
        if (!service.getUser().getId().equals(user.getId())){
            throw new SecurityException("Acesso negado: Você não é o dono deste serviço.");
        }

        monitoredApiRepository.delete(service);
    }
}
