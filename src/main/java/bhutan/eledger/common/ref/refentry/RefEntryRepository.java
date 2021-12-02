package bhutan.eledger.common.ref.refentry;

public interface RefEntryRepository {

    RefEntry findByRefNameAndId(String refName, Long id);

}
