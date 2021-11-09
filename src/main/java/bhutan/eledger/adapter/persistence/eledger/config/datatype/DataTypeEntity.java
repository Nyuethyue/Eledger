package bhutan.eledger.adapter.persistence.eledger.config.datatype;

import bhutan.eledger.domain.eledger.config.datatype.DataType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data_type", schema = "eledger_config")
@AllArgsConstructor
@NoArgsConstructor
class DataTypeEntity implements DataType {

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
