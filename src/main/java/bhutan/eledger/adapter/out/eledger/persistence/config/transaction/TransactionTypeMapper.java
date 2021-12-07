package bhutan.eledger.adapter.out.eledger.persistence.config.transaction;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.eledger.config.transaction.TransactionType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class TransactionTypeMapper {

    TransactionTypeEntity mapToEntity(TransactionType transactionType) {

        var transactionTypeEntity = new TransactionTypeEntity(
                transactionType.getId(),
                transactionType.getCode()
        );

        transactionTypeEntity.setDescriptions(
                transactionType.getDescription()
                        .getTranslations()
                        .stream()
                        .map(t ->
                                new TransactionTypeDescriptionEntity(
                                        t.getId(),
                                        t.getLanguageCode(),
                                        t.getValue(),
                                        transactionTypeEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return transactionTypeEntity;
    }

    TransactionType mapToDomain(TransactionTypeEntity transactionTypeEntity) {
        return TransactionType.withId(
                transactionTypeEntity.getId(),
                transactionTypeEntity.getCode(),
                Multilingual.of(transactionTypeEntity.getDescriptions())
        );
    }
}
