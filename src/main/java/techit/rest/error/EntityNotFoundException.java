package techit.rest.error;

public class EntityNotFoundException extends RestException {
	
	private static final long serialVersionUID = -3184651275018955051L;

	public EntityNotFoundException(Class<?> entityClass) {
		super(404, entityClass + " does not exist.");
	}
	
	public EntityNotFoundException(Object entity) {
		this(entity.getClass());
	}
		
}
