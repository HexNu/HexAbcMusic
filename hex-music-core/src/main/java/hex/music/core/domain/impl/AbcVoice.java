package hex.music.core.domain.impl;

import hex.music.core.domain.Clef;
import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hln
 */
@Entity
@Table(name = "Voice")
public class AbcVoice implements Voice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String subname;
    @Column
    private String voiceCode;
    @Column(length = 32 * KB)
    private String body;
    @Column(length = 8 * KB)
    private String searchString;
    @Column
    private int voiceIndex;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = AbcClef.class)
    private Clef clef;
    @ManyToOne(targetEntity = AbcTune.class)
    private Tune tune;

    public AbcVoice() {
        this(0);
    }

    public AbcVoice(int index) {
        this(index, new AbcClef());
    }

    public AbcVoice(int index, Clef clef) {
        this.voiceIndex = index;
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
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSubname() {
        return subname;
    }

    @Override
    public void setSubname(String subname) {
        this.subname = subname;
    }

    @Override
    public String getVoiceCode() {
        return voiceCode;
    }

    @Override
    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode;
    }

    @Override
    public int getVoiceIndex() {
        return voiceIndex;
    }

    @Override
    public void setVoiceIndex(int index) {
        this.voiceIndex = index;
    }

    @Override
    public Clef getClef() {
        return clef;
    }

    @Override
    public void setClef(Clef clef) {
        this.clef = clef;
    }

    @Override
    public Tune getTune() {
        return tune;
    }

    @Override
    public void setTune(Tune tune) {
        this.tune = tune;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void addBodyLine(String line) {
        if (body == null) {
            body = "";
        }
        body += line + "\n";
    }

    @Override
    public String getSearchString() {
        return searchString;
    }

    @Override
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
