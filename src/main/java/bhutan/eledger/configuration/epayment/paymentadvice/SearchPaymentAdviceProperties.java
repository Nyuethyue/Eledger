package bhutan.eledger.configuration.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.AbstractSearchProperties;
import bhutan.eledger.adapter.out.epayment.persistence.paymentadvice.QPaymentAdviceEntity;
import bhutan.eledger.common.constants.CharSequenceConstants;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.epayment.payment.advice.search")
@ConstructorBinding
@Getter
@Validated
public class SearchPaymentAdviceProperties extends AbstractSearchProperties {
    public SearchPaymentAdviceProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QPaymentAdviceEntity.paymentAdviceEntity.id.getMetadata().getName(),
                        QPaymentAdviceEntity.paymentAdviceEntity.drn.getMetadata().getName(),
                        QPaymentAdviceEntity.paymentAdviceEntity.pan.getMetadata().getName(),
                        QPaymentAdviceEntity.paymentAdviceEntity.taxpayer.getMetadata().getName() + CharSequenceConstants.DOT + QPaymentAdviceEntity.paymentAdviceEntity.taxpayer.tpn.getMetadata().getName(),
                        QPaymentAdviceEntity.paymentAdviceEntity.dueDate.getMetadata().getName()
                )
        );
    }
}
