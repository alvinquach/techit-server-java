package techit.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import techit.model.Position;
import techit.model.Unit;
import techit.model.User;
import techit.model.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User getUser(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User getUserByUsername(String username) {
		try {
			return entityManager.createQuery("from User where username = :username", User.class)
					.setParameter("username", username)
					.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<User> getUsers() {
		return entityManager.createQuery("from User order by id", User.class)
				.getResultList();
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		return entityManager.merge(user);
	}

	@Override
	public List<User> getUsersByUnit(Unit unit) {
		return entityManager.createQuery("from User where unit = :unit", User.class)
				.setParameter("unit", unit)
				.getResultList();
	}

	@Override
	public List<User> getUsersByUnitAndPosition(Unit unit, Position position) {
		return entityManager.createQuery("from User where unit = :unit and position = :position", User.class)
				.setParameter("unit", unit)
				.setParameter("position", position)
				.getResultList();
	}

	@Override
	public List<User> getTechniciansByUnit(Unit unit) {
		return entityManager.createQuery("from User where unit = :unit and position = :position", User.class)
				.setParameter("unit", unit)
				.setParameter("position", Position.TECHNICIAN)
				.getResultList();
		
	}

	@Override
	public List<User> getSupervisorsByUnit(Unit unit) {
		return entityManager.createQuery("from User where unit = :unit and position = :position", User.class)
				.setParameter("unit", unit)
				.setParameter("position", Position.SUPERVISING_TECHNICIAN)
				.getResultList();
		
	}

}
