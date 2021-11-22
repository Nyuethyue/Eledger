package bhutan.eledger.adapter.in.web.eledger.config.glaccount.history;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.glaccount.history.GetGLAccountHistoryUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/config/gl/account/history")
@RequiredArgsConstructor
class GLAccountHistoryController {
    private final GetGLAccountHistoryUseCase getGLAccountHistoryUseCase;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<GLAccountHistoryPresentation> historiesById(@PathVariable Long id) {
        return getGLAccountHistoryUseCase.getHistoriesById(id)
                .stream()
                .map(bah -> new GLAccountHistoryPresentation(
                                bah.getDto().getCode(),
                                bah.getDto().getDescription(),
                                bah.getDto().getStatus().getValue(),
                                bah.getDto().getCreationDateTime(),
                                bah.getDto().getLastModificationDateTime(),
                                bah.getDto().getActualDateTime(),
                                bah.getMetadata().getUsername(),
                                bah.getMetadata().getHistoryType().value()
                        )
                ).collect(Collectors.toUnmodifiableList());
    }

    @Data
    private static class GLAccountHistoryPresentation {
        private final String code;
        private final Multilingual description;
        private final String status;
        private final LocalDateTime creationDateTime;
        private final LocalDateTime modificationDateTime;
        private final LocalDateTime actualDateTime;
        private final String username;
        private final String action;
    }
}
