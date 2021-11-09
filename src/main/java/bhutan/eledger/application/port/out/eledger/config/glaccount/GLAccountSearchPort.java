package bhutan.eledger.application.port.out.eledger.config.glaccount;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import lombok.Getter;
import lombok.ToString;

public interface GLAccountSearchPort {

    SearchResult<GLAccount> search(GLAccountSearchCommand command);

    @Getter
    @ToString
    class GLAccountSearchCommand extends AbstractSearchCommand {

        private final String languageCode;
        private final String code;
        private final String head;

        public GLAccountSearchCommand(int page, int size, String sortProperty, String sortDirection, String languageCode, String code, String head) {
            super(page, size, sortProperty, sortDirection);
            this.languageCode = languageCode;
            this.code = code;
            this.head = head;
        }
    }
}
