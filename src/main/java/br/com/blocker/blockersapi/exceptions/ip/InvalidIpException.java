package br.com.blocker.blockersapi.exceptions.ip;

public class InvalidIpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6313852700334637740L;

	public InvalidIpException(final String message) {
		super(message);
	}
}