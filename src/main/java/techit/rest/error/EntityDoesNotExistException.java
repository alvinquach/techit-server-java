package techit.rest.error;

public class EntityDoesNotExistException extends RestException {
	
	private static final long serialVersionUID = -3184651275018955051L;

	public EntityDoesNotExistException(Class<?> entityClass) {
		super(404, entityClass + " does not exist.");
	}
	
	public EntityDoesNotExistException(Object entity) {
		this(entity.getClass());
	}
		
}
