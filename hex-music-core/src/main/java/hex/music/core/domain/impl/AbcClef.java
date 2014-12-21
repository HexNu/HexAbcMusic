package hex.music.core.domain.impl;

import hex.music.core.domain.Clef;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hln
 */
@Entity
@Table(name = "Clef")
public class AbcClef implements Clef {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type = DEFAULT_TYPE;
    @Column
    private int transpose = DEFAULT_TRANSPOSE;
    @Column
    private String middle = DEFAULT_MIDDLE;

    public AbcClef() {
    }

    public AbcClef(Type type) {
    }

    public AbcClef(Type type, int transpose) {
        this.type = type;
        this.transpose = transpose;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return type.getLabel();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int getTranspose() {
        return transpose;
    }

    @Override
    public void setTranspose(int transpose) {
        this.transpose = transpose;
    }

    @Override
    public String getMiddle() {
        return middle;
    }

    @Override
    public void setMiddle(String middle) {
        this.middle = middle;
    }
}
