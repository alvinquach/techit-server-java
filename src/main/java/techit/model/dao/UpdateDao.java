 package techit.model.dao;

import java.util.List;


import techit.model.Update;

public interface UpdateDao {
    Update getUpdate (Integer l );

    List<Update> getUpdate();

    Update saveUpdate( Update update );
    
    
    
}

