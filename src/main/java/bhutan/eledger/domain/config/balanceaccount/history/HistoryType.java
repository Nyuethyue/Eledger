package bhutan.eledger.domain.config.balanceaccount.history;

public enum HistoryType {
    CREATED,
    MODIFIED,
    DELETED;

    public static HistoryType fromRevisionType(String revisionType) {
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

    public String value() {
        return name();
    }
}
