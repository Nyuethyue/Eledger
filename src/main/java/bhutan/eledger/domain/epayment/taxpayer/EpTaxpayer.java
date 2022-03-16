package bhutan.eledger.domain.epayment.taxpayer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "\"Taxpayer\"", schema = "taxpayer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class EpTaxpayer {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tpn")
    private String tpn;

    @Column(name = "\"tpName\"")
    private String name;
}
