package bhutan.eledger.adapter.out.eledger.persistence.config.transaction;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class TransactionTypeAttributeMapper {

    TransactionTypeAttributeEntity mapToEntity(TransactionTypeAttribute transactionTypeAttribute) {

        var transactionTypeAttributeEntity = new TransactionTypeAttributeEntity(
                transactionTypeAttribute.getId(),
                transactionTypeAttribute.getCode(),
                transactionTypeAttribute.getDataType().getId()
        );

        transactionTypeAttributeEntity.setDescriptions(
                transactionTypeAttribute.getDescription()
                        .getTranslations()
                        .stream()
                        .map(t ->
                                new TransactionTypeAttributeDescriptionEntity(
                                        t.getId(),
                                        t.getLanguageCode(),
                                        t.getValue(),
                                        transactionTypeAttributeEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return transactionTypeAttributeEntity;
    }

    TransactionTypeAttribute mapToDomain(TransactionTypeAttributeEntity transactionTypeAttributeEntity, DataType dataType) {
        return TransactionTypeAttribute.withId(
                transactionTypeAttributeEntity.getId(),
                transactionTypeAttributeEntity.getCode(),
                dataType,
                Multilingual.of(transactionTypeAttributeEntity.getDescriptions())
        );
    }
}
