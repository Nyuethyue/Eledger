package bhutan.eledger.adapter.in.eledger.scheduler;

import bhutan.eledger.application.port.in.eledger.accounting.AccountingCalculationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Log4j2
@Component
@RequiredArgsConstructor
//todo ShedLock when multiple instances is up
class AccountingCalculationScheduler {
    private final AccountingCalculationUseCase accountingCalculationUseCase;

    //todo sync. What if startup will match the cron time
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {

        //todo must be start date be a startup date? What if application is down and people didn't do payment for that reason.
        doJob(LocalDateTime.now());
    }

    @Scheduled(cron = "${bhutan.eledger.schedule.accounting.calculation.cron}")
    void onSchedule() {
        doJob(LocalDateTime.now());
    }

    private void doJob(LocalDateTime startDateTime) {

        log.info("Accounting calculation scheduled job started at: {}", startDateTime);

        accountingCalculationUseCase.calculate(startDateTime.toLocalDate());

        var endDateTime = LocalDateTime.now();

        log.info("Accounting calculation scheduled job ended at: {}, Duration: {}.", () -> endDateTime, () -> Duration.between(startDateTime, endDateTime));

    }
}
