package bhutan.eledger.adapter.out.eledger.persistence.accounting;

import bhutan.eledger.application.port.out.eledger.accounting.GetPaymentAdviceDataPort;
import bhutan.eledger.domain.eledger.accounting.PaymentAdviceData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
class GetPaymentAdviceDataAdapter implements GetPaymentAdviceDataPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Collection<PaymentAdviceData> get(String tpn, LocalDate formulationDate) {
        //language=PostgreSQL
        var countQuery = "SELECT * FROM eledger.fn_get_payment_advice_data(:tpn, :formulationDate)";


        var result = jdbcTemplate.query(
                countQuery,
                Map.of(
                        "tpn", tpn,
                        "formulationDate", formulationDate
                ),
                this::mapToPaymentAdviceData
        );

        log.trace("Payment advice queried data by tpn: {} and formulationDate: {} is: {}", tpn, formulationDate, result);

        return result;
    }

    private PaymentAdviceData mapToPaymentAdviceData(ResultSet resultSet, int i) throws SQLException {
        String tpn = resultSet.getString("tpn");
        String tpName = resultSet.getString("tp_name");
        String drn = resultSet.getString("drn");
        String periodYear = resultSet.getString("period_year");
        String periodSegment = resultSet.getString("period_segment");
        LocalDate deadline = resultSet.getDate("deadline").toLocalDate();

        PGobject paymentLinesPGobject = resultSet.getObject("payable_lines", PGobject.class);

        Collection<PaymentAdviceData.PayableLine> payableLines;
        try {
            payableLines = objectMapper.readValue(
                    paymentLinesPGobject.getValue(),
                    new TypeReference<>() {}
            );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(
                    "Cannot parse payment_lines value to: " + PaymentAdviceData.PayableLine.class + ". Value: " + paymentLinesPGobject.getValue(),
                    e
            );
        }

        return PaymentAdviceData.of(
                drn,
                PaymentAdviceData.Taxpayer.of(
                        tpn,
                        tpName
                ),
                deadline,
                PaymentAdviceData.Period.of(
                        periodYear,
                        periodSegment
                ),
                payableLines
        );
    }


}
