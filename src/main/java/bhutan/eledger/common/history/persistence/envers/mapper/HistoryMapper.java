package bhutan.eledger.common.history.persistence.envers.mapper;

import bhutan.eledger.common.history.HistoriesHolder;
import bhutan.eledger.common.history.History;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class HistoryMapper {
    private final HistoryMetadataMapper historyMetadataMapper;

    public <R, T> HistoriesHolder<R> mapToHistoriesHolder(Revisions<Long, T> revisions, Function<T, R> entityToDtoMapper) {
        var revisionsStream = revisions.stream();

        return revisionStreamToHistoriesHolder(revisionsStream, entityToDtoMapper, Comparator.comparing(h -> h.getMetadata().getRevId()));
    }

    public <R, T> HistoriesHolder<R> mapToHistoriesHolder(Page<Revision<Long, T>> revisions, Function<T, R> mapToDomain) {
        return revisionStreamToHistoriesHolder(revisions.stream(), mapToDomain);
    }

    private <R, T> HistoriesHolder<R> revisionStreamToHistoriesHolder(Stream<Revision<Long, T>> revisionsStream, Function<T, R> entityToDtoMapper) {
        return revisionStreamToHistoriesHolder(revisionsStream, entityToDtoMapper, null);
    }

    private <R, T> HistoriesHolder<R> revisionStreamToHistoriesHolder(Stream<Revision<Long, T>> revisionsStream, Function<T, R> entityToDtoMapper, Comparator<? super History<R>> comparator) {
        var stream = revisionsStream
                .map(r ->
                        History.of(
                                entityToDtoMapper.apply(r.getEntity()),
                                historyMetadataMapper.fromRevisionMetadata(r.getMetadata())
                        )
                );

        if (comparator != null) {
            stream = stream.sorted(comparator);
        }

        return stream
                .collect(Collectors.collectingAndThen(
                                Collectors.toUnmodifiableList(),
                                HistoriesHolder::of
                        )
                );
    }
}
