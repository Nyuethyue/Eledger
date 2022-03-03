package bhutan.eledger.adapter.out.eledger.persistence.refund;

import bhutan.eledger.application.port.out.eledger.refund.GetRefundableDataByTaxTypesPort;
import bhutan.eledger.application.port.out.eledger.refund.GetRefundableDataByTransactionIdsPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountPart;
import bhutan.eledger.domain.eledger.refund.RefundableTransactionData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
class GetRefundableTransactionDataAdapter implements GetRefundableDataByTaxTypesPort, GetRefundableDataByTransactionIdsPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Collection<RefundableTransactionData> getByTaxTypes(String tpn, LocalDate calculationDate, Collection<GLAccountPart> taxTypes) {
        var query = "SELECT * FROM eledger.fn_get_refundable_data_by_tax_type_codes(:tpn, :taxTypeCodes, :calculationDate)";

        return jdbcTemplate.query(
                query,
                Map.of(
                        "tpn", tpn,
                        "taxTypeCodes", taxTypes
                                .stream()
                                .map(GLAccountPart::getFullCode)
                                .toArray(String[]::new),
                        "calculationDate", calculationDate
                ),
                this::mapToRefundableTransactionData
        );
    }

    @Override
    public Collection<RefundableTransactionData> getByTransactionIds(String tpn, LocalDate calculationDate, Collection<Long> transactionIds) {
        var query = "SELECT * FROM eledger.fn_get_refundable_data_by_transaction_ids(:tpn, :transactionIds, :calculationDate)";

        return jdbcTemplate.query(
                query,
                Map.of(
                        "tpn", tpn,
                        "transactionIds", transactionIds.toArray(Long[]::new),
                        "calculationDate", calculationDate
                ),
                this::mapToRefundableTransactionData
        );
    }

    private RefundableTransactionData mapToRefundableTransactionData(ResultSet resultSet, int i) throws SQLException {
        return RefundableTransactionData.of(
                resultSet.getString("net_negative_type"),
                resultSet.getLong("transaction_id"),
                resultSet.getString("tax_type_code"),
                resultSet.getString("gl_account_code"),
                resultSet.getLong("gl_account_id"),
                resultSet.getString("drn"),
                resultSet.getString("period_year"),
                resultSet.getString("period_segment"),
                resultSet.getBigDecimal("balance")
        );
    }
}
