package bhutan.eledger.application.service.ref.nonworkingdays;

import bhutan.eledger.application.port.in.ref.nonworkingdays.ReadRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.out.ref.nonworkingdays.RefNonWorkingDaysRepositoryPort;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadNonWorkingDaysService implements ReadRefNonWorkingDaysUseCase {
    private final RefNonWorkingDaysRepositoryPort refNonWorkingDaysRepositoryPort;

    @Override
    public Collection<RefNonWorkingDays> readAll() {
        log.trace("Reading all non working days.");

        return refNonWorkingDaysRepositoryPort.readAll();
    }

    @Override
    public int countNonWorkingDays(LocalDate from, LocalDate to) {
//        log.trace("Calculating non working days in interval from {} to {}", from, to);
//        Collection<RefNonWorkingDays> days = refNonWorkingDaysRepositoryPort.readAll();
//        int count = 0;
//        if(null != days) {
//            int fromYear = from.getYear();
//            int toYear = to.getYear();
//            for(RefNonWorkingDays nonWorkingDay : days) {
//                if(fromYear <= nonWorkingDay.getYear() && nonWorkingDay.getYear() <= toYear) {
//                    LocalDate startDate = nonWorkingDay.getStartDate();
//                    LocalDate endDate = nonWorkingDay.getEndDate();
//                    while (startDate.isBefore(endDate)) {
//                        if(startDate.isAfter(from) && startDate.isBefore(to)
//                        startDate = startDate.plusDays(1);
//                    }
//                }
//            }
//        }
//        return count;
        return 0;
    }
}
