package hcmute.kltn.vtv.model.entity.vtc;

import hcmute.kltn.vtv.model.entity.location.District;
import hcmute.kltn.vtv.model.entity.location.Ward;
import hcmute.kltn.vtv.model.extra.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Deliver {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID deliverId;

    private String phone;

    private String email;

    private String province;

    private String district;

    private String ward;

    private String fullAddress;

    private String typeWork;

    private String usernameAdded;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_code")
    private District districtWork;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "deliver_ward",
            joinColumns = @JoinColumn(name = "deliver_id"),
            inverseJoinColumns = @JoinColumn(name = "ward_code"))
    private List<Ward> wardsWork;
}
