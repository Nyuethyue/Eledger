package bhutan.eledger.adapter.out.eledger.persistence.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.eledger.reporting.taxpayeraccount.TaxpayerAccountSspSearchPort;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountSspDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
class TaxpayerAccountSspSearchAdapter implements TaxpayerAccountSspSearchPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public SearchResult<TaxpayerAccountSspDto> search(TaxpayerAccountSspSearchCommand command) {

        var content = queryContent(command);

        return SearchResult.unpaged(
                content
        );
    }


    private List<TaxpayerAccountSspDto> queryContent(TaxpayerAccountSspSearchCommand command) {
        //language=PostgreSQL
        var resultQuery = "SELECT * FROM eledger.fn_taxpayer_account_ssp_report(:tpn, :glAccountPartFullCode, :periodYear, :periodSegment, :languageCode)";


        return jdbcTemplate.query(
                resultQuery,
                resolveSqlParameterSource(command),
                this::mapResultSetToDto
        );
    }

    private TaxpayerAccountSspDto mapResultSetToDto(ResultSet rs, int rowIndex) throws SQLException {
        return TaxpayerAccountSspDto.of(
                rs.getString("row_type"),
                rs.getDate("transaction_date").toLocalDate(),
                rs.getString("description"),
                rs.getBigDecimal("debit"),
                rs.getBigDecimal("credit"),
                rs.getBigDecimal("balance"),
                rs.getString("drn")
        );
    }

    private MapSqlParameterSource resolveSqlParameterSource(TaxpayerAccountSspSearchCommand command) {
        return new MapSqlParameterSource()
                .addValue("tpn", command.getTpn())
                .addValue("languageCode", command.getLanguageCode())
                .addValue("glAccountPartFullCode", command.getGlAccountPartFullCode())
                .addValue("periodYear", command.getPeriodYear())
                .addValue("periodSegment", command.getPeriodSegment());
    }
}
