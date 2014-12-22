package hex.music.mysql.dao;

import hex.music.core.domain.Tune;
import hex.music.core.domain.impl.AbcTune;
import hex.music.core.domain.impl.ResultListWrapper;
import java.math.BigInteger;
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

    public ResultListWrapper getLimitedTuneList(int limit, int offset) {
        String condition = "FROM `Tune`";
        List<Tune> resultList = (List<Tune>) getManager().createNativeQuery("SELECT * " + condition
                + " ORDER BY `title` LIMIT " + limit + " OFFSET " + offset, AbcTune.class).getResultList();
        BigInteger max = (BigInteger) getManager().createNativeQuery("SELECT count(*) " + condition).getSingleResult();
        ResultListWrapper result = getLimitedResult(resultList, limit, offset, condition, null);
        return result;
    }

    public ResultListWrapper getSearchResult(int limit, int offset, String query) {
        String condition = "FROM `Tune` WHERE LOWER(`title`) LIKE ('%" + query.toLowerCase() + "%') ";
        List<Tune> resultList = (List<Tune>) getManager().createNativeQuery("SELECT * " + condition
                + "ORDER BY `title` LIMIT " + limit + " OFFSET " + offset, AbcTune.class).getResultList();
        ResultListWrapper result = getLimitedResult(resultList, limit, offset, condition, query);
        return result;
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
        return getManager().createNativeQuery("SELECT DISTINCT `source` FROM `Tune`  WHERE `source` IS NOT NULL ORDER BY `source`").getResultList();
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

    private ResultListWrapper getLimitedResult(List<Tune> resultList, int limit, int offset, String condition, String query) {
        BigInteger max = (BigInteger) getManager().createNativeQuery("SELECT count(*) " + condition).getSingleResult();
        ResultListWrapper result = new ResultListWrapper(resultList, limit, query);
        Integer previous = offset - limit >= 0 ? offset - limit : null;
        result.setPrevious(previous);
        Integer next = offset + limit < max.intValue() ? offset + limit : null;
        result.setNext(next);
        return result;
    }
}
