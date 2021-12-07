package bhutan.eledger.configuration.eledger.config.glaccount;

import am.iunetworks.lib.common.persistence.search.AbstractSearchProperties;
import bhutan.eledger.adapter.out.eledger.persistence.config.glaccount.QGLAccountEntity;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.eledger.glaccount.search")
@ConstructorBinding
@Getter
@Validated
public class GLAccountSearchProperties extends AbstractSearchProperties {
    public GLAccountSearchProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QGLAccountEntity.gLAccountEntity.id.getMetadata().getName(),
                        QGLAccountEntity.gLAccountEntity.code.getMetadata().getName(),
                        QGLAccountEntity.gLAccountEntity.startOfValidity.getMetadata().getName(),
                        QGLAccountEntity.gLAccountEntity.creationDateTime.getMetadata().getName(),
                        QGLAccountEntity.gLAccountEntity.lastModificationDateTime.getMetadata().getName(),
                        QGLAccountEntity.gLAccountEntity.status.getMetadata().getName()
                )
        );
    }
}
