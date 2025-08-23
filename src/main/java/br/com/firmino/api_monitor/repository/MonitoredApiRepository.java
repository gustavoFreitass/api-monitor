package br.com.firmino.api_monitor.repository;

import br.com.firmino.api_monitor.api.MonitoredApi;
import br.com.firmino.api_monitor.api.ServiceType;
import br.com.firmino.api_monitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoredApiRepository extends JpaRepository<MonitoredApi, Long> {
    List<MonitoredApi>findByUser(User user);

    List<MonitoredApi> findByUserAndServiceType(User user, ServiceType serviceType);

    List<MonitoredApi> findByIsPublicTrue();
}
