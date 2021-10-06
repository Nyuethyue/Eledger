package bhutan.eledger.adapter.persistence.config.datatype;

import bhutan.eledger.domain.config.datatype.DataType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data_type", schema = "config")
@AllArgsConstructor
@NoArgsConstructor
public class DataTypeEntity implements DataType {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
