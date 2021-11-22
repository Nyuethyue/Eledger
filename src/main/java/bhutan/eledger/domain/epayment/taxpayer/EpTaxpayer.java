package bhutan.eledger.domain.epayment.taxpayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ep_taxpayer", schema = "epayment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EpTaxpayer {
    @Id
    @SequenceGenerator(name = "ep_taxpayer_id_seq", schema = "epayment", sequenceName = "ep_taxpayer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_taxpayer_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "tpn")
    private String tpn;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    public static EpTaxpayer withoutId(
            String tpn,
            String name,
            LocalDateTime creationDateTime
    ) {
        return new EpTaxpayer(
                null,
                tpn,
                name,
                creationDateTime
        );
    }
}
