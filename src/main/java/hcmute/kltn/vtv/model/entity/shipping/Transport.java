package hcmute.kltn.vtv.model.entity.shipping;

import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.extra.TransportStatus;
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
public class Transport {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    private String wardCodeShop;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private TransportStatus transportStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "transport",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<TransportHandle> transportHandles;
}
