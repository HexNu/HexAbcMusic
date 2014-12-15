package hex.music.core.domain.impl;

import hex.music.core.domain.Clef;
import hex.music.core.domain.Key;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hln
 */
@Entity
@Table(name = "AbcKey")
public class AbcKey implements Key {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = AbcClef.class)
    private Clef clef;

    public AbcKey() {
        this(Type.DEFAULT_TYPE);
    }
    
    public AbcKey(Type type) {
        this(type, Clef.DEFAULT_CLEF);
    }

    public AbcKey(Type type, Clef clef) {
        this.type = type;
        this.clef = clef;
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
    public Clef getClef() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setClef(Clef clef) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
