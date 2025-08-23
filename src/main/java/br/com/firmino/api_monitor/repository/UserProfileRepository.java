package br.com.firmino.api_monitor.repository;

import br.com.firmino.api_monitor.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
