package bhutan.eledger.adapter.out.persistence.eledger.reporting.glaccountdetails;

import am.iunetworks.lib.common.persistence.search.PagedSearchResult;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.out.eledger.reporting.glaccountdetails.GLAccountDetailsSearchPort;
import bhutan.eledger.domain.eledger.reporting.glaccountdetails.GlAccountDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
class GLAccountDetailsSearchAdapter implements GLAccountDetailsSearchPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public SearchResult<GlAccountDetailsDto> search(GLAccountDetailsSearchCommand command) {

        var content = queryContent(command);
        var totalCount = queryTotalCount(command);

        return PagedSearchResult.of(
                content,
                PageRequest.of(
                        command.getPage(),
                        command.getSize()
                ),
                totalCount
        );
    }

    private Long queryTotalCount(GLAccountDetailsSearchCommand command) {
        //language=PostgreSQL
        var countQuery = "SELECT * FROM eledger.fn_gl_accounting_details_count(:tpn, :languageCode, :glAccountPartFullCode, :periodYear, :periodSegment, :startTransactionDate, :endTransactionDate)";


        return jdbcTemplate.queryForObject(
                countQuery,
                resolveSqlParameterSource(command),
                Long.class
        );
    }

    private List<GlAccountDetailsDto> queryContent(GLAccountDetailsSearchCommand command) {
        //language=PostgreSQL
        var resultQuery = "SELECT * FROM eledger.fn_gl_accounting_details(:tpn, :languageCode, :glAccountPartFullCode, :periodYear, :periodSegment, :startTransactionDate, :endTransactionDate, :offset, :limit)";


        return jdbcTemplate.query(
                resultQuery,
                resolveSqlParameterSource(command)
                        .addValue("offset", command.getPage() * command.getSize())
                        .addValue("limit", command.getSize()),
                this::mapResultSetToDto
        );
    }

    private GlAccountDetailsDto mapResultSetToDto(ResultSet rs, int rowIndex) throws SQLException {
        return GlAccountDetailsDto.of(
                rs.getDate("transaction_date").toLocalDate(),
                rs.getString("gl_account_id"),
                rs.getString("gl_account_code"),
                rs.getString("accounting_description"),
                rs.getString("period_year"),
                rs.getString("period_segment"),
                rs.getBigDecimal("amount"),
                rs.getBigDecimal("net_negative"),
                rs.getBigDecimal("total_liability"),
                rs.getBigDecimal("total_interest"),
                rs.getBigDecimal("total_penalty"),
                rs.getBigDecimal("payment"),
                rs.getBigDecimal("non_revenue"),
                rs.getString("drn"),
                rs.getString("tpn")
        );
    }

    private MapSqlParameterSource resolveSqlParameterSource(GLAccountDetailsSearchCommand command) {
        return new MapSqlParameterSource()
                .addValue("tpn", command.getTpn())
                .addValue("languageCode", command.getLanguageCode())
                .addValue("glAccountPartFullCode", command.getGlAccountPartFullCode())
                .addValue("periodYear", command.getPeriodYear())
                .addValue("periodSegment", command.getPeriodSegment())
                .addValue("startTransactionDate", command.getStartTransactionDate())
                .addValue("endTransactionDate", command.getEndTransactionDate());
    }
}
