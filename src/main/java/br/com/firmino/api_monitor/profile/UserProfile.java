package br.com.firmino.api_monitor.profile;

import br.com.firmino.api_monitor.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    private String displayName;
    private String bio;
    private String company;
    private String location;
    private String websiteUrl;
    private String profileImageUrl;
}
