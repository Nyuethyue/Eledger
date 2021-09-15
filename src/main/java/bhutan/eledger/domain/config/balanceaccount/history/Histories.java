package bhutan.eledger.domain.config.balanceaccount.history;

import lombok.Data;
import org.springframework.data.util.Streamable;

import java.util.Iterator;
import java.util.List;

@Data(staticConstructor = "of")
public class Histories<T> implements Streamable<History<T>> {
    private final List<History<T>> histories;

    @Override
    public Iterator<History<T>> iterator() {
        return histories.iterator();
    }
}
