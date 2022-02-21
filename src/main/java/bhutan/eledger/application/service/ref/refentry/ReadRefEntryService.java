package bhutan.eledger.application.service.ref.refentry;

import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefEntryService implements RefEntryRepository {
    private final RefCurrencyRepositoryPort refCurrencyRepositoryPort;
    private final RefBankBranchRepositoryPort refBankBranchRepositoryPort;


    @Override
    public RefEntry findByRefNameAndId(String refName, Long id) {
        log.trace("Reading ref entry. Ref name: {}, ID: {}.", refName, id);

        if (id == null) {
            return null;
        }

        RefEntry result;

        if (RefCurrency.class.getSimpleName().equals(refName)) {
            var refCurrency = refCurrencyRepositoryPort.requiredReadById(id);

            result = RefEntry.builder(
                            refCurrency.getId(),
                            refCurrency.getCode()
                    )
                    .description(refCurrency.getDescription())
                    .addAttribute("symbol", refCurrency.getSymbol())
                    .build();
        } else if (RefBankBranch.class.getSimpleName().equals(refName)) {
            var refCurrency = refBankBranchRepositoryPort.requiredReadById(id);

            result = RefEntry.builder(
                            refCurrency.getId(),
                            refCurrency.getCode()
                    )
                    .description(refCurrency.getDescription())
                    .addAttribute("branchCode", refCurrency.getBranchCode())
                    .addAttribute("address", refCurrency.getAddress())
                    .addAttribute("bankId", refCurrency.getBankId().toString())
                    .build();
        } else {
            throw new IllegalArgumentException("Illegal ref name: " + refName);
        }

        return result;
    }

    @Override
    public RefEntry findByRefNameAndCode(String refName, String code) {
        log.trace("Reading ref entry. Ref name: {}, code: {}.", refName, code);

        if (code == null) {
            return null;
        }

        RefEntry result;

        if (RefCurrency.class.getSimpleName().equals(refName)) {
            var refCurrency = refCurrencyRepositoryPort.requiredReadByCode(code);

            result = RefEntry.builder(
                            refCurrency.getId(),
                            refCurrency.getCode()
                    )
                    .description(refCurrency.getDescription())
                    .addAttribute("symbol", refCurrency.getSymbol())
                    .build();
        } else {
            throw new IllegalArgumentException("Illegal ref name: " + refName);
        }

        return result;

    }
}
