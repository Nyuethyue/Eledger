package bhutan.eledger.common.history.persistence.envers.mapper;

import bhutan.eledger.common.history.Histories;
import bhutan.eledger.common.history.History;
import lombok.RequiredArgsConstructor;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistoryMapper {
    private final HistoryMetadataMapper historyMetadataMapper;

    public <R, T> Histories<R> map(Revisions<Long, T> revisions, Function<T, R> f) {
        return revisions.stream()
                .map(r ->
                        History.of(
                                f.apply(r.getEntity()),
                                historyMetadataMapper.fromRevisionMetadata(r.getMetadata())
                        )
                )
                .collect(Collectors.collectingAndThen(
                                Collectors.toUnmodifiableList(),
                                Histories::of
                        )
                );

    }
}
