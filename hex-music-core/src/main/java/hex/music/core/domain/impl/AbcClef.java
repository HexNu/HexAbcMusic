package hex.music.core.domain.impl;

import hex.music.core.domain.Clef;
import hex.music.core.domain.Voice;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    private final Type type;
    @Column
    private final int transpose;
    @OneToOne(mappedBy = "clef", fetch = FetchType.EAGER, targetEntity = AbcVoice.class)
    private Voice voice;
    @Transient
    public static final int DEFAULT_TRANSPOSE = 0;

    public AbcClef() {
        this(Type.DEFAULT_TYPE, DEFAULT_TRANSPOSE);
    }

    public AbcClef(Type type) {
        this(type, DEFAULT_TRANSPOSE);
    }

    public AbcClef(Type type, int transpose) {
        this.type = type;
        this.transpose = transpose;
    }

    @Override
    public Voice getVoice() {
        return voice;
    }

    @Override
    public void setVoice(Voice voice) {
        this.voice = voice;
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
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public int getTranspose() {
        return transpose;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (name != null && !name.equals("")) {
            builder.append(name).append(" ");
        }

        return builder.toString();
    }
}
