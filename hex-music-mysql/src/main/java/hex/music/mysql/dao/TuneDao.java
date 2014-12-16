package hex.music.mysql.dao;

import hex.music.core.domain.Tune;
import hex.music.core.domain.impl.AbcTune;
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
}
