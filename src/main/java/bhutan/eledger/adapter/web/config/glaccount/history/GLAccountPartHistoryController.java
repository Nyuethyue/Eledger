package bhutan.eledger.adapter.web.config.glaccount.history;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.glaccount.history.GetGLAccountPartHistoryUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/config/gl/account/part/history")
@RequiredArgsConstructor
class GLAccountPartHistoryController {
    private final GetGLAccountPartHistoryUseCase getGLAccountPartHistoryUseCase;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<GLAccountPartHistoryPresentation> historiesById(@PathVariable Long id) {

        return getGLAccountPartHistoryUseCase.getHistoriesById(id)
                .stream()
                .map(bah -> new GLAccountPartHistoryPresentation(
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
    private static class GLAccountPartHistoryPresentation {
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
