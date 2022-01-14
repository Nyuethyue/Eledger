package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DepositAdapter implements DepositRepositoryPort {
    private final DepositMapper depositMapper;
    private final DepositEntityRepository depositEntityRepository;
    private final FlatReceiptLoaderBean flatReceiptLoaderBean;


    @Override
    public Optional<Deposit> readById(Long id) {
        return depositEntityRepository.findById(id)
                .map(depositEntity-> depositMapper.mapToDomain(depositEntity, flatReceiptLoaderBean.loadReceiptIdToFlatReceiptMap(depositEntity)));
    }

    @Override
    public Optional<Deposit> readByDepositNumber(String depositNumber) {
        return depositEntityRepository.readByDepositNumber(depositNumber)
                .map(depositEntity-> depositMapper.mapToDomain(depositEntity, flatReceiptLoaderBean.loadReceiptIdToFlatReceiptMap(depositEntity)));
    }

    @Override
    public Collection<Deposit> readAll() {
        Collection<DepositEntity> rawDeposits = depositEntityRepository.findAll();
        return rawDeposits
                .stream()
                .map(depositEntity-> depositMapper.mapToDomain(depositEntity, flatReceiptLoaderBean.loadReceiptIdToFlatReceiptMap(rawDeposits)))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Deposit create(Deposit deposit) {
        DepositEntity depositEntity = depositEntityRepository.save(
                depositMapper.mapToEntity(deposit)
        );

        return depositMapper.mapToDomain(depositEntity, flatReceiptLoaderBean.loadReceiptIdToFlatReceiptMap(depositEntity));
    }

    @Override
    public void deleteAll() {
        depositEntityRepository.deleteAll();
    }

    @Override
    public void update(Deposit deposit) {
        depositEntityRepository.save(
                depositMapper.mapToEntity(deposit)
        );
    }

    @Override
    public void updateStatus(Long depositId, DepositStatus status) {
        DepositEntity depositEntity = depositEntityRepository.getById(depositId);
        depositEntity.setStatus(status.getValue());
        depositEntityRepository.save(depositEntity);
    }
}
