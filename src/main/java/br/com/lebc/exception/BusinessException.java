package br.com.lebc.exception;

/**
 * Class to be used when a Business Exception happen.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
public class BusinessException extends Exception {

	public BusinessException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -8686770846389214122L;

}
