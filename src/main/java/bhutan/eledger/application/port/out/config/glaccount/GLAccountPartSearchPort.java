package bhutan.eledger.application.port.out.config.glaccount;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import lombok.Getter;
import lombok.ToString;

public interface GLAccountPartSearchPort {

    SearchResult<GLAccountPart> search(GLAccountPartSearchCommand command);

    @Getter
    @ToString
    class GLAccountPartSearchCommand extends AbstractSearchCommand {

        private final String languageCode;
        private final String code;
        private final String head;
        private final Integer partTypeId;

        public GLAccountPartSearchCommand(int page, int size, String sortProperty, String sortDirection, String languageCode, String code, String head, Integer partTypeId) {
            super(page, size, sortProperty, sortDirection);
            this.languageCode = languageCode;
            this.code = code;
            this.head = head;
            this.partTypeId = partTypeId;
        }
    }
}
