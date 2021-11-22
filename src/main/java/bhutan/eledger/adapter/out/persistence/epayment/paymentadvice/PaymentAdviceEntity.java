package bhutan.eledger.adapter.out.persistence.epayment.paymentadvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_payment_advice", schema = "epayment")
@Getter
@Setter
class PaymentAdviceEntity {
    @Id
    @SequenceGenerator(name = "ep_payment_advice_id_seq", sequenceName = "ep_payment_advice_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_payment_advice_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "drn")
    private String drn;

    @Column(name = "tpn")
    private String tpn;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "period_start_date")
    private LocalDate periodStartDate;

    @Column(name = "period_end_date")
    private LocalDate periodEndDate;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "pan")
    private String pan;

    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_bank_info_id", nullable = false)
    private PaymentAdviceBankInfoEntity bankInfo;

    @OneToMany(
            mappedBy = "paymentAdvice",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PaymentLineEntity> paymentLines;

    public PaymentAdviceEntity(Long id, String drn, String tpn, LocalDate dueDate, LocalDate periodStartDate, LocalDate periodEndDate, LocalDateTime creationDateTime, String pan, String status, PaymentAdviceBankInfoEntity bankInfo) {
        this.id = id;
        this.drn = drn;
        this.tpn = tpn;
        this.dueDate = dueDate;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.creationDateTime = creationDateTime;
        this.pan = pan;
        this.status = status;
        this.bankInfo = bankInfo;
    }
}
