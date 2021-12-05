package bhutan.eledger.application.port.out.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public interface DepositSearchPort {

    SearchResult<Deposit> search(DepositSearchCommand command);

    @Getter
    @ToString
    class DepositSearchCommand extends AbstractSearchCommand {
        private final Long id;
        private final LocalDate fromBankDepositDate;
        private final LocalDate toBankDepositDate;

        public DepositSearchCommand(int page, int size, String sortProperty, String sortDirection,
                              Long id, LocalDate fromBankDepositDate, LocalDate toBankDepositDate) {
            super(page, size, sortProperty, sortDirection);
            this.id = id;
            this.fromBankDepositDate = fromBankDepositDate;
            this.toBankDepositDate = toBankDepositDate;
        }
    }
}
