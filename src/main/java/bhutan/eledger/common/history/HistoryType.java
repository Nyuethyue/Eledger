package bhutan.eledger.common.history;

public enum HistoryType {
    CREATED,
    MODIFIED,
    DELETED;

    public String value() {
        return name();
    }
}
