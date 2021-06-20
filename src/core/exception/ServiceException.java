package core.exception;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5037273685217964782L;

	private ExceptionCode exceptionCode;
	private String[] parameters;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

}
