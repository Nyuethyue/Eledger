package bhutan.eledger.configuration.epayment.payment;

import am.iunetworks.lib.common.persistence.search.AbstractSearchProperties;
import bhutan.eledger.adapter.out.persistence.epayment.payment.QReceiptEntity;
import bhutan.eledger.common.constants.CharSequenceConstants;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.epayment.payment.receipt.search")
@ConstructorBinding
@Getter
@Validated
public class ReceiptSearchProperties extends AbstractSearchProperties {
    public ReceiptSearchProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QReceiptEntity.receiptEntity.id.getMetadata().getName(),
                        QReceiptEntity.receiptEntity.receiptNumber.getMetadata().getName(),
                        QReceiptEntity.receiptEntity.creationDateTime.getMetadata().getName(),
                        QReceiptEntity.receiptEntity.status.getMetadata().getName(),
                        QReceiptEntity.receiptEntity.instrumentDate.getMetadata().getName(),
                        QReceiptEntity.receiptEntity.taxpayer.getMetadata().getName() + CharSequenceConstants.DOT + QReceiptEntity.receiptEntity.taxpayer.tpn.getMetadata().getName()
                )
        );
    }
}
