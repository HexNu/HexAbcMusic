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
    private Signature signature;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = AbcClef.class)
    private Clef clef;

    public AbcKey() {
        this(Signature.DEFAULT_TYPE);
    }
    
    public AbcKey(Signature type) {
        this(type, new AbcClef());
    }

    public AbcKey(Signature type, Clef clef) {
        this.signature = type;
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
        return signature.getLabel();
    }

    @Override
    public Signature getSignature() {
        return signature;
    }

    @Override
    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    @Override
    public Clef getClef() {
        return clef;
    }

    @Override
    public void setClef(Clef clef) {
        this.clef = clef;
    }
}
