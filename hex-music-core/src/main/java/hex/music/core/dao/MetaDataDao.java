package hex.music.core.dao;

import hex.music.core.domain.MetaData;
import hex.music.core.domain.impl.AbcMetaData;
import javax.persistence.EntityManager;

/**
 *
 * @author hln
 */
public class MetaDataDao extends GenericDao<MetaData, Long> {

    public MetaDataDao(EntityManager entityManager) {
        super(AbcMetaData.class, entityManager);
    }

    @Override
    public MetaData save(MetaData metaData) {
        if (metaData.getId() == null) {
            return super.save(metaData);
        } else {
            return super.update(metaData);
        }
    }

}
