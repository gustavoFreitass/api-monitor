package br.com.firmino.api_monitor.api;

import br.com.firmino.api_monitor.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoredApiRepository extends JpaRepository<MonitoredApi, Long> {
    List<MonitoredApi>findByUser(User user);

    List<MonitoredApi>findByUseAndServiceType(User user, ServiceType serviceType);

    List<MonitoredApi> findByIsPublicTrue();
}
