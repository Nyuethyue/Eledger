package bhutan.eledger.common.history.persistence.envers.mapper;

import bhutan.eledger.common.history.HistoryMetadata;
import bhutan.eledger.common.history.HistoryType;
import bhutan.eledger.common.history.persistence.envers.UsernameAwareRevisionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static bhutan.eledger.common.history.HistoryType.*;

@Component
@RequiredArgsConstructor
public class HistoryMetadataMapper {

    public HistoryMetadata fromRevisionMetadata(RevisionMetadata<Long> revisionMetadata) {
        UsernameAwareRevisionEntity revision = revisionMetadata.getDelegate();

        return HistoryMetadata.of(
                revision.getId(),
                Instant.ofEpochMilli(revision.getTimestamp()),
                revision.getUsername(),
                fromRevisionType(revisionMetadata.getRevisionType().name())
        );
    }

    private static HistoryType fromRevisionType(String revisionType) {
        switch (revisionType) {
            case "INSERT":
                return CREATED;
            case "UPDATE":
                return MODIFIED;
            case "DELETE":
                return DELETED;
            default:
                throw new IllegalArgumentException("Unknown revisionType: " + revisionType);
        }
    }
}
