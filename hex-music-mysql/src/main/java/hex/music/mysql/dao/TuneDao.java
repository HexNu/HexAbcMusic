package hex.music.mysql.dao;

import hex.music.core.domain.Tune;
import hex.music.core.domain.TuneListWrapper;
import hex.music.core.domain.Voice;
import hex.music.core.domain.impl.AbcTune;
import hex.music.core.domain.impl.ResultListWrapper;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author hln
 */
public class TuneDao extends GenericDao<Tune, Long> {

    private static final String[] fields = {"bibliography", "composer",
        "discography", "history", "notes", "region", "rythm", "source",
        "subheader", "title", "transcriber"};

    public TuneDao(EntityManager entityManager) {
        super(AbcTune.class, entityManager);
    }

    public TuneListWrapper getLimitedTuneList(int limit, int offset) {
        String condition = "FROM `Tune` AS t ";
        List<Tune> resultList = (List<Tune>) getManager().createNativeQuery("SELECT t.* " + condition
                + " ORDER BY t.`title` LIMIT " + limit + " OFFSET " + offset, AbcTune.class).getResultList();
        BigInteger max = (BigInteger) getManager().createNativeQuery("SELECT count(*) " + condition).getSingleResult();
        TuneListWrapper result = getLimitedResult(resultList, limit, offset, condition, null, null);
        return result;
    }

    public TuneListWrapper getSearchResult(int limit, int offset, String query) {
        String condition = "FROM `Tune` AS t  WHERE " + createCondition(query);
        List<Tune> resultList = (List<Tune>) getManager().createNativeQuery("SELECT t.* " + condition
                + " ORDER BY t.`title` LIMIT " + limit + " OFFSET " + offset, AbcTune.class).getResultList();
        TuneListWrapper result = getLimitedResult(resultList, limit, offset, condition, query, null);
        return result;
    }

    public TuneListWrapper getNoteSearchResult(int limit, int offset, String notes) {
        String condition = "FROM `Tune` AS t INNER JOIN Voice AS v ON t.`id` = v.`tune_id` WHERE v.`searchString` LIKE '%"
                + createNoteSearchString(notes) + "%'";
        String nativeQuery = "SELECT t.* " + condition + " ORDER BY t.`title` LIMIT " + limit + " OFFSET " + offset;
        List<Tune> resultList = (List<Tune>) getManager().createNativeQuery(nativeQuery, AbcTune.class).getResultList();
        TuneListWrapper result = getLimitedResult(resultList, limit, offset, condition, null, notes);
        return result;
    }

    public List<String> getComposers() {
        return getManager().createNativeQuery("SELECT DISTINCT `composer` FROM `Tune`  "
                + "WHERE `composer` IS NOT NULL ORDER BY `composer`").getResultList();
    }

    public List<String> getSources() {
        return getManager().createNativeQuery("SELECT DISTINCT `source` FROM `Tune`  "
                + "WHERE `source` IS NOT NULL ORDER BY `source`").getResultList();
    }

    public List<String> getRegions() {
        return getManager().createNativeQuery("SELECT DISTINCT `region` FROM `Tune` "
                + " WHERE `region` IS NOT NULL ORDER BY `region`").getResultList();
    }

    public List<String> getRythms() {
        return getManager().createNativeQuery("SELECT DISTINCT `rythm` FROM `Tune`  "
                + "WHERE `rythm` IS NOT NULL ORDER BY `rythm`").getResultList();
    }

    public List<String> getTranscribers() {
        return getManager().createNativeQuery("SELECT DISTINCT `transcriber` FROM `Tune`  "
                + "WHERE `transcriber` IS NOT NULL ORDER BY `transcriber`").getResultList();
    }

    @Override
    public Tune save(Tune tune) {
        for (Voice voice : tune.getVoices()) {
            voice.setSearchString(createNoteSearchString(voice.getBody()));
        }
        if (tune.getId() == null) {
            return super.save(tune);
        } else {
            return super.update(tune);
        }
    }

    private String createCondition(String queryString) {
        StringBuilder result = new StringBuilder();
        String[] queryParts = queryString.split("\\s");
        boolean initialized = false;
        for (String queryPart : Arrays.asList(queryParts)) {
            for (String field : Arrays.asList(fields)) {
                if (initialized) {
                    result.append(" OR ");
                }
                result.append("t.`").append(field).append("` LIKE '%").append(queryPart).append("%'");
                initialized = true;
            }
        }
        return result.toString();
    }

    private String createNoteSearchString(String body) {
        StringBuilder result = new StringBuilder();
        StringReader reader = new StringReader(body.toLowerCase());
        boolean cordStarted = false;
        boolean accentStarted = false;
        boolean inlineChangeStarted = false;
        boolean wordLineStarted = false;
        String lastNote = "";
        for (int i = 0; i < body.length(); i++) {
            try {
                String s = Character.toString((char) reader.read());
                if (s.equals("\"")) {
                    cordStarted = !cordStarted;
                }
                if (s.equals("[")) {
                    inlineChangeStarted = true;
                }
                if (s.equals("]")) {
                    inlineChangeStarted = false;
                }
                if (s.equals("w")) {
                    wordLineStarted = true;
                }
                if (wordLineStarted && s.equals("\n")) {
                    wordLineStarted = false;
                }
                if (s.equals("!")) {
                    accentStarted = !accentStarted;
                }
                if (!cordStarted && !accentStarted && !inlineChangeStarted && !wordLineStarted
                        && !s.equals(lastNote) && s.matches("[a-g]")) {
                    result.append(s);
                    lastNote = s;
                }
            } catch (IOException ex) {
                Logger.getLogger(TuneDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result.toString();
    }

    private TuneListWrapper getLimitedResult(List<Tune> resultList, int limit, int offset, String condition, String query, String notes) {
        BigInteger max = (BigInteger) getManager().createNativeQuery("SELECT count(*) " + condition).getSingleResult();
        TuneListWrapper result = new ResultListWrapper(resultList, limit, query, notes);
        Integer previous = offset - limit >= 0 ? offset - limit : null;
        result.setPrevious(previous);
        Integer next = offset + limit < max.intValue() ? offset + limit : null;
        result.setNext(next);
        return result;
    }
}
