package hex.music.mysql.dao;

import hex.music.core.domain.Tune;
import hex.music.core.domain.impl.AbcTune;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hln
 */
public class TuneDao extends GenericDao<Tune, Long> {

    public TuneDao(EntityManager entityManager) {
        super(AbcTune.class, entityManager);
    }

    @Override
    public Tune save(Tune tune) {
        if (tune.getId() == null) {
            return super.save(tune);
        } else {
            return super.update(tune);
        }
    }

    public List<String> getComposers() {
        return getManager().createNativeQuery("SELECT DISTINCT `composer` FROM `Tune`  WHERE `composer` IS NOT NULL ORDER BY `composer`").getResultList();
    }

    public List<String> getSources() {
        return getManager().createNativeQuery("SELECT DISTINCT `originator` FROM `Tune`  WHERE `originator` IS NOT NULL ORDER BY `originator`").getResultList();
    }

    public List<String> getRegions() {
        return getManager().createNativeQuery("SELECT DISTINCT `region` FROM `Tune`  WHERE `region` IS NOT NULL ORDER BY `region`").getResultList();
    }

    public List<String> getRythms() {
        return getManager().createNativeQuery("SELECT DISTINCT `rythm` FROM `Tune`  WHERE `rythm` IS NOT NULL ORDER BY `rythm`").getResultList();
    }

    public List<String> getTranscribers() {
        return getManager().createNativeQuery("SELECT DISTINCT `transcriber` FROM `Tune`  WHERE `transcriber` IS NOT NULL ORDER BY `transcriber`").getResultList();
    }
}
