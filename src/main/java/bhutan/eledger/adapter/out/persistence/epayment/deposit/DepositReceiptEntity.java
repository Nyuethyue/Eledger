package bhutan.eledger.adapter.out.persistence.epayment.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposit_receipt", schema = "epayment")
@Getter
@Setter
class DepositReceiptEntity {
    @Id
    @SequenceGenerator(name = "epayment.deposit_receipt_id_seq", sequenceName = "epayment.deposit_receipt_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "epayment.deposit_receipt_id_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "receipt_id")
    private Long receiptId;

    @ManyToOne
    @JoinColumn(name = "deposit_id", nullable = false)
    private DepositEntity deposit;

    public DepositReceiptEntity(Long id, Long receiptId) {
        this.id = id;
        this.receiptId = receiptId;
    }

    public void setDeposit(DepositEntity deposit) {
        this.deposit = deposit;
    }

}
