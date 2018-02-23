package techit.model.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import techit.model.Unit;
import techit.model.dao.UnitDao;

@Repository
public class UnitDaoImpl implements UnitDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Unit getUnit( Long id )
    {
        return entityManager.find( Unit.class, id );
    }

    @Override
    @Transactional
    public Unit saveUnit( Unit unit )
    {
        return entityManager.merge( unit );
    }

}
