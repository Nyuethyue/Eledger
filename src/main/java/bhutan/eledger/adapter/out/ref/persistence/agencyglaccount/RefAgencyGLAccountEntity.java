package bhutan.eledger.adapter.out.ref.persistence.agencyglaccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "agency_gl_account", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefAgencyGLAccountEntity {
    @Id
    @SequenceGenerator(name = "agency_gl_account_id_seq", schema = "ref", sequenceName = "agency_gl_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agency_gl_account_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "agency_code")
    private String agencyCode;

    public RefAgencyGLAccountEntity(Long id, String code,  String agencyCode) {
        this.id = id;
        this.code = code;
        this.agencyCode = agencyCode;
    }

}
