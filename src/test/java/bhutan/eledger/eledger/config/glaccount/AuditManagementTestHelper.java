package bhutan.eledger.eledger.config.glaccount;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class AuditManagementTestHelper {

    private final JdbcTemplate jdbcTemplate;

    AuditManagementTestHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void clearAudTables() {
        jdbcTemplate.execute(
                "DELETE FROM eledger_config.gl_account_description_aud; "
                        + "DELETE FROM eledger_config.gl_account_aud; "
                        + "DELETE FROM eledger_config.gl_account_part_description_aud; "
                        + "DELETE FROM eledger_config.gl_account_part_aud; "
                        + "DELETE FROM public.revision; "
        );
    }

}
