 package techit.model.dao;

import techit.model.Update;

public interface UpdateDao {
	
    Update getUpdate(Long id);

    Update saveUpdate(Update update);
    
}