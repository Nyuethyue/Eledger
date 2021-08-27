package bhutan.eledger.configuration.config.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.search.AbstractSearchProperties;
import bhutan.eledger.adapter.persistence.config.balanceaccount.QBalanceAccountEntity;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.eledger.balanceaccount.search")
@ConstructorBinding
@Getter
@Validated
public class BalanceAccountSearchProperties extends AbstractSearchProperties {
    public BalanceAccountSearchProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QBalanceAccountEntity.balanceAccountEntity.id.getMetadata().getName(),
                        QBalanceAccountEntity.balanceAccountEntity.code.getMetadata().getName(),
                        QBalanceAccountEntity.balanceAccountEntity.startDate.getMetadata().getName(),
                        QBalanceAccountEntity.balanceAccountEntity.creationDateTime.getMetadata().getName(),
                        QBalanceAccountEntity.balanceAccountEntity.lastModificationDateTime.getMetadata().getName(),
                        QBalanceAccountEntity.balanceAccountEntity.status.getMetadata().getName()
                )
        );
    }
}
