package bhutan.eledger.configuration.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.AbstractSearchProperties;
import bhutan.eledger.adapter.out.epayment.persistence.deposit.QDepositEntity;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.epayment.payment.deposit.search")
@ConstructorBinding
@Getter
@Validated
public class DepositSearchProperties extends AbstractSearchProperties {
    public DepositSearchProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QDepositEntity.depositEntity.id.getMetadata().getName(),
                        QDepositEntity.depositEntity.bankDepositDate.getMetadata().getName(),
                        QDepositEntity.depositEntity.creationDateTime.getMetadata().getName(),
                        QDepositEntity.depositEntity.status.getMetadata().getName(),
                        QDepositEntity.depositEntity.depositDenominations.getMetadata().getName(),
                        QDepositEntity.depositEntity.amount.getMetadata().getName(),
                        QDepositEntity.depositEntity.lastPrintedDate.getMetadata().getName(),
                        QDepositEntity.depositEntity.depositReceipts.getMetadata().getName()
                )
        );
    }
}
