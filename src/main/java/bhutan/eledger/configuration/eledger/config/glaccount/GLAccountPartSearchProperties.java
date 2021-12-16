package bhutan.eledger.configuration.eledger.config.glaccount;

import am.iunetworks.lib.common.persistence.search.AbstractSearchProperties;
import bhutan.eledger.adapter.out.eledger.persistence.config.glaccount.QGLAccountPartEntity;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Set;

@ConfigurationProperties(prefix = "bhutan.eledger.glaccount.part.search")
@ConstructorBinding
@Getter
@Validated
public class GLAccountPartSearchProperties extends AbstractSearchProperties {
    public GLAccountPartSearchProperties(int page, int size, String sortProperty, String sortDirection, Collection<String> availableSortProperties) {
        super(
                page,
                size,
                sortProperty,
                sortDirection,
                availableSortProperties,
                Set.of(
                        QGLAccountPartEntity.gLAccountPartEntity.id.getMetadata().getName(),
                        QGLAccountPartEntity.gLAccountPartEntity.code.getMetadata().getName(),
                        QGLAccountPartEntity.gLAccountPartEntity.creationDateTime.getMetadata().getName(),
                        QGLAccountPartEntity.gLAccountPartEntity.lastModificationDateTime.getMetadata().getName()
                )
        );
    }
}
