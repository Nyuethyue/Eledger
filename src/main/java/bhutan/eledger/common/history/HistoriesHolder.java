package bhutan.eledger.common.history;

import lombok.Data;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.List;

@Data(staticConstructor = "of")
public class HistoriesHolder<T> implements Streamable<History<T>> {
    private final List<History<T>> histories;

    @NonNull
    @Override
    public Iterator<History<T>> iterator() {
        return histories.iterator();
    }
}
