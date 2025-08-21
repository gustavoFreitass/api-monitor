package br.com.firmino.api_monitor.api;

import br.com.firmino.api_monitor.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoredApiService {
    @Autowired
    private MonitoredApiRepository monitoredApiRepository;

    public List<MonitoredApi> getServicesForUser(User user){
        return monitoredApiRepository.findByUser(user);
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

    public List<MonitoredApi> getPublicService(){
        return monitoredApiRepository.findByIsPublicTrue();
    }
}
