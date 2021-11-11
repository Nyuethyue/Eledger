package bhutan.eledger.domain.eledger.taxpayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "el_taxpayer", schema = "eledger")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Taxpayer {
    @Id
    @SequenceGenerator(name = "el_taxpayer_id_seq", schema = "eledger", sequenceName = "el_taxpayer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_taxpayer_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "tpn")
    private String tpn;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    public static Taxpayer withoutId(
            String tpn,
            LocalDateTime creationDateTime
    ) {
        return new Taxpayer(
                null,
                tpn,
                creationDateTime
        );
    }
}
