package techit.rest.error;

public class MissingFieldsException extends RestException {
	
	private static final long serialVersionUID = -6316127577283355378L;

	public MissingFieldsException(Class<?> entityClass) {
		super(400, "Required fields are missing in the " + entityClass.getSimpleName() + " object.");
	}
	
	public MissingFieldsException(Object entity) {
		this(entity.getClass());
	}
		
}
