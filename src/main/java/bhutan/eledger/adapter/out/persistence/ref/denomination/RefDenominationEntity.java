package bhutan.eledger.adapter.out.persistence.ref.denomination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "denomination", schema = "ref")
@Getter
@Setter
class RefDenominationEntity {
    @Id
    @SequenceGenerator(name = "denomination_id_seq", schema = "ref", sequenceName = "denomination_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "denomination_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "val")
    private String value;

    public RefDenominationEntity(Long id, String value) {
        this.id = id;
        this.value = value;
    }
}
