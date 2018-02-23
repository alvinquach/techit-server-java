package techit.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Update;
import techit.model.User;
import techit.model.dao.TicketDao;
import techit.model.dao.UpdateDao; 


@Repository
public class UpdateDaoImpl implements UpdateDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Update getUpdate(long l) {

		return entityManager.find( Update.class, l );
	}


	@Override
	public List<Update> getUpdate() {
	       return entityManager.createQuery( "from Updates order by update_id", Update.class )
	                .getResultList();
	}
    
    @Override
    @Transactional
    public Update saveUpdate( Update update )
    {
        return entityManager.merge( update );
    }

}


