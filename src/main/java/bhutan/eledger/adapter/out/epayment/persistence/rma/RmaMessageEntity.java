package bhutan.eledger.adapter.out.epayment.persistence.rma;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ep_rma_message", schema = "epayment")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@DynamicUpdate
class RmaMessageEntity {

    @Id
    @SequenceGenerator(name = "ep_rma_message_id_seq", schema = "epayment", sequenceName = "ep_rma_message_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_rma_message_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_advice_id")
    private Long paymentAdviceId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rma_message_ar_part_id")
    private RmaMessageArPartEntity arPart;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rma_message_as_part_id")
    private RmaMessageAsPartEntity asPart;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @OneToMany(
            mappedBy = "rmaMessage",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RmaMessageResponseEntity> rmaMessageResponses;

    public RmaMessageEntity(Long id, String orderNo, String status, Long paymentAdviceId, LocalDateTime creationDateTime, RmaMessageArPartEntity arPart, RmaMessageAsPartEntity asPart) {
        this.id = id;
        this.orderNo = orderNo;
        this.status = status;
        this.paymentAdviceId = paymentAdviceId;
        this.creationDateTime = creationDateTime;
        this.arPart = arPart;
        this.asPart = asPart;
    }
}
