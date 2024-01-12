package hcmute.kltn.vtv.model.entity.vtc.manager;

import hcmute.kltn.vtv.model.entity.vtc.Customer;
import hcmute.kltn.vtv.model.entity.vtc.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ManagerProduct {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerProductId;

    private String note;

    @Column(name = "is_lock")
    private boolean lock;

    @Column(name = "is_delete")
    private boolean delete;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Customer manager;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
}
