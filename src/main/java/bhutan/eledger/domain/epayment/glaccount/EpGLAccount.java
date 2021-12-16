package bhutan.eledger.domain.epayment.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "ep_gl_account", schema = "epayment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EpGLAccount {
    @Id
    @SequenceGenerator(name = "ep_gl_account_id_seq", schema = "epayment", sequenceName = "ep_gl_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_gl_account_id_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Transient
    private Multilingual description;

    @OneToMany(
            mappedBy = "glAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Set<EpGLAccountDescription> descriptions;

    private EpGLAccount(String code, LocalDateTime creationDateTime, Multilingual description) {
        this.code = code;
        this.creationDateTime = creationDateTime;
        this.description = description;
    }

    protected Set<EpGLAccountDescription> getDescriptions() {
        return descriptions;
    }

    @PostLoad
    private void initDescription() {
        description = Multilingual.of(getDescriptions());
    }

    public static EpGLAccount withoutId(String code, LocalDateTime creationDateTime, Multilingual description) {
        EpGLAccount glAccount = new EpGLAccount(code, creationDateTime, description);

        glAccount.descriptions = description.getTranslations()
                .stream()
                .map(t ->
                        new EpGLAccountDescription(
                                t.getId(),
                                t.getLanguageCode(),
                                t.getValue(),
                                glAccount
                        )
                )
                .collect(Collectors.toSet());

        return glAccount;
    }
}
