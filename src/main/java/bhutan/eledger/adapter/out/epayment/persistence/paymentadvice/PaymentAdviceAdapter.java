package bhutan.eledger.adapter.out.epayment.persistence.paymentadvice;

import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PaymentAdviceAdapter implements PaymentAdviceRepositoryPort {
    private final PaymentAdviceMapper paymentAdviceMapper;
    private final PaymentAdviceEntityRepository paymentAdviceEntityRepository;

    @Override
    public Optional<PaymentAdvice> readById(Long id) {
        return paymentAdviceEntityRepository.findById(id)
                .map(paymentAdviceMapper::mapToDomain);
    }

    @Override
    public Optional<PaymentAdvice> readByDrnAndStatusIn(String drn, Collection<PaymentAdviceStatus> statuses) {


        return paymentAdviceEntityRepository.findByDrnAndStatusIn(
                drn,
                statuses
                        .stream()
                        .map(PaymentAdviceStatus::getValue)
                        .collect(Collectors.toUnmodifiableSet())
        ).map(paymentAdviceMapper::mapToDomain);
    }

    @Override
    public Collection<PaymentAdvice> readAllByIds(Collection<Long> ids) {
        return paymentAdviceEntityRepository.findAllById(ids)
                .stream()
                .map(paymentAdviceMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Collection<PaymentAdvice> readAll() {
        return paymentAdviceEntityRepository.findAll()
                .stream()
                .map(paymentAdviceMapper::mapToDomain)
                .toList();
    }

    @Override
    public Long create(PaymentAdvice paymentAdvice) {
        PaymentAdviceEntity paymentAdviceEntity = paymentAdviceEntityRepository.save(
                paymentAdviceMapper.mapToEntity(paymentAdvice)
        );

        return paymentAdviceEntity.getId();
    }

    @Override
    public void deleteAll() {
        paymentAdviceEntityRepository.deleteAll();
    }

    @Override
    public void update(PaymentAdvice updatedPaymentAdvice) {
        paymentAdviceEntityRepository.save(
                paymentAdviceMapper.mapToEntity(updatedPaymentAdvice)
        );
    }

    @Override
    public void updateStatus(PaymentAdvice updatedPaymentAdvice) {
        paymentAdviceEntityRepository.updateStatus(
                updatedPaymentAdvice.getId(),
                updatedPaymentAdvice.getStatus().getValue()
        );
    }

    @Override
    public Optional<FlatPaymentAdvice> readFlatById(Long id) {
        return paymentAdviceEntityRepository.readById(id);
    }

    @Override
    public Collection<FlatPaymentAdvice> readAllFlatByDrns(Collection<String> drns) {
        return paymentAdviceEntityRepository.findAllByDrnIn(drns);
    }
}
