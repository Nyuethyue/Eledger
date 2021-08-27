package bhutan.eledger.application.port.out.config.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.spring.search.SearchResult;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.Getter;
import lombok.ToString;

public interface BalanceAccountSearchPort {

    SearchResult<BalanceAccount> search(BalanceAccountSearchCommand command);

    @Getter
    @ToString
    class BalanceAccountSearchCommand extends AbstractSearchCommand {

        private final String languageCode;
        private final String code;
        private final String head;

        public BalanceAccountSearchCommand(int page, int size, String sortProperty, String sortDirection, String languageCode, String code, String head) {
            super(page, size, sortProperty, sortDirection);
            this.languageCode = languageCode;
            this.code = code;
            this.head = head;
        }
    }
}
