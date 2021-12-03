package bhutan.eledger.adapter.out.persistence.epayment.deposit;

import bhutan.eledger.adapter.out.persistence.epayment.payment.ReceiptEntity;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposit", schema = "epayment")
@Getter
@Setter
class DepositEntity {
    @Id
    @SequenceGenerator(name = "deposit_id_seq", sequenceName = "deposit_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_id_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "bank_deposit_date")
    private LocalDate bankDepositDate;
    @Column(name = "last_printed_date")
    private LocalDate lastPrintedDate;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    private String status;
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private ReceiptEntity receipt;

}
