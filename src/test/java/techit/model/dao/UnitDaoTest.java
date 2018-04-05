package techit.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import techit.model.Unit;

@Test(groups = "UnitDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UnitDaoTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Autowired
	UnitDao unitDao;

	@Test
	public void getUnit() {
		assert unitDao.getUnit(1L).getId() == 1L;
	}
	
	@Test
	public void getUnits() {
		assert unitDao.getUnits().size()>1;
	}

	@Test
	public void saveUnit() {
		Unit unit = new Unit();
		unit.setName("emg");
		
		unit = unitDao.saveUnit(unit);
		assert unit.getId() != null;
	}

}
