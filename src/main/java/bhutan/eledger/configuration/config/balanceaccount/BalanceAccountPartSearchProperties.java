package bhutan.eledger.configuration.config.balanceaccount;

import am.iunetworks.lib.common.persistence.search.AbstractSearchProperties;
import bhutan.eledger.adapter.persistence.config.balanceaccount.QBalanceAccountPartEntity;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.eledger.balanceaccount.part.search")
@ConstructorBinding
@Getter
@Validated
public class BalanceAccountPartSearchProperties extends AbstractSearchProperties {
    public BalanceAccountPartSearchProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QBalanceAccountPartEntity.balanceAccountPartEntity.id.getMetadata().getName(),
                        QBalanceAccountPartEntity.balanceAccountPartEntity.code.getMetadata().getName(),
                        QBalanceAccountPartEntity.balanceAccountPartEntity.startDate.getMetadata().getName(),
                        QBalanceAccountPartEntity.balanceAccountPartEntity.creationDateTime.getMetadata().getName(),
                        QBalanceAccountPartEntity.balanceAccountPartEntity.lastModificationDateTime.getMetadata().getName(),
                        QBalanceAccountPartEntity.balanceAccountPartEntity.status.getMetadata().getName()
                )
        );
    }
}
