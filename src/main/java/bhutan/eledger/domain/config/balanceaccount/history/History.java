package bhutan.eledger.domain.config.balanceaccount.history;

import lombok.Data;

@Data(staticConstructor = "of")
public class History<T> {
    private final T dto;
    private final HistoryMetadata metadata;
}
