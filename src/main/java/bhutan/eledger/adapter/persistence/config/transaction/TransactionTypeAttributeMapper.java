package bhutan.eledger.adapter.persistence.config.transaction;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.datatype.DataType;
import bhutan.eledger.domain.config.transaction.TransactionTypeAttribute;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class TransactionTypeAttributeMapper {

    TransactionTypeAttributeEntity mapToEntity(TransactionTypeAttribute transactionTypeAttribute) {

        var transactionTypeAttributeEntity = new TransactionTypeAttributeEntity(
                transactionTypeAttribute.getId(),
                transactionTypeAttribute.getName(),
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
                transactionTypeAttributeEntity.getName(),
                dataType,
                Multilingual.of(transactionTypeAttributeEntity.getDescriptions())
        );
    }
}
