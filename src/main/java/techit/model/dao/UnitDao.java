package techit.model.dao;

import techit.model.Unit;

public interface UnitDao {

    Unit getUnit( Long id );

    Unit saveUnit( Unit unit );

}
