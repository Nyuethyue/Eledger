package bhutan.eledger.application.port.in.ref.agencyglaccount;

import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;

@Validated
public interface CreateRefAgencyGLAccountUseCase {
    Collection<RefAgencyGLAccount> create(@Valid CreateRefAgencyGLAccountUseCase.CreateAgencyGlAccountCommand command);

    @Data
    class CreateAgencyGlAccountCommand {
        @NotNull
        private final String agencyCode;

        @NotNull
        @Valid
        private final Collection<AgencyGlAccountCommand> agencyGlAccounts;
    }

    @Data
    class AgencyGlAccountCommand {
        @NotNull
        private final String code;

        @JsonCreator
        public AgencyGlAccountCommand(@JsonProperty("code") String code) {
            this.code = code;
        }
    }
}
