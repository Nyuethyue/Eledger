package bhutan.eledger.common.history;

import lombok.Data;

@Data(staticConstructor = "of")
public class History<T> {
    private final T dto;
    private final HistoryMetadata metadata;
}
