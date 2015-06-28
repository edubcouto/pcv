package br.com.lebc.exception;

/**
 * Class to be used when a Data Exception happen.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
public class DataException extends Exception {

	private static final long serialVersionUID = -3148257637502791371L;

	public DataException(String message){
		super(message);
	}
	
}
