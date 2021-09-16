package bhutan.eledger.common.history;

import lombok.Data;

import java.time.Instant;

@Data(staticConstructor = "of")
public class HistoryMetadata {
    private final Long revId;
    private final Instant instant;
    private final String username;
    private final HistoryType historyType;
}
