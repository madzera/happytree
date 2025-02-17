package com.madzera.happytree.exception;

/**
 * The class <code>TreeException</code> represents a checked exception for the
 * HappyTree API, by inheriting the {@link Exception} class.
 * 
 * <p>The <code>TreeException</code> is the only one exception (checked
 * exception) class that can be threw inside of this API. This happens when the
 * following validations does not corresponds:</p>
 * 
 * <ul>
 * 	<li>When there is another session with the same identifier, by initializing
 * 	a new session;</li>
 * 
 * 	<li>When the class of the object to be transformed has not been annotated by
 * 	{@code @Tree}, {@code @Id} and {@code @Parent}, when initializing a
 * 	new session;</li>
 * 
 * 	<li>When the annotated attribute {@code @Id} and {@code @Parent} have
 * 	different types;</li>
 * 
 * 	<li>When the transaction has no session selected to work;</li>
 * 
 * 	<li>When the current session is not active;</li>
 * 
 * 	<li>When there are mismatch parameterized types when invoking the operations;
 * 	</li>
 * 
 * 	<li>When there is an attempt of handling an element which does not belong to
 * 	the current session;</li>
 * 
 * 	<li>When there is an attempt of handling the root element of the tree;</li>
 * 
 * 	<li>When the element to be cut/copied/removes is not with the
 * 	<i>ATTACHED</i> state in life cycle;</li>
 * 
 * 	<li>When the element to be updated is with the <i>NOT_EXISTED</i> state
 * 	in life cycle;</li>
 * 
 * 	<li>When the element to be persisted is not with the <i>NOT_EXISTED</i>
 * 	state in life cycle;</li>
 * 
 * 	<li>When there is an <code>Element</code> object with duplicate id, by
 * 	trying to insert/update or cut/copy an element between the trees;</li>
 * </ul>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public class TreeException extends Exception {
	private static final long serialVersionUID = 9097414119963988912L;
	
	/**
	 * Default construct for this exception. This invocation implies a
	 * <code>null</code> details message of the exception as well as the cause.
	 * 
	 * <p>To initialize a cause, invoke the {@link #initCause(Throwable)}.</p>
	 */
	public TreeException() {
		super();
	}
	
	/**
	 * This construct allows to specifier a detail message of the exception when
	 * it is threw. In this case, the cause of exception is <code>null</code>.
	 * 
	 * <p>To initialize a cause, invoke the {@link #initCause(Throwable)}.</p>
	 * 
	 * @param message the detail message of the exception
	 */
	public TreeException(String message) {
		super(message);
	}
	
	/**
	 * Create an exception with the specified <code>cause</code>. In this case,
	 * the message of the exception will be the message associated to the
	 * <code>cause</code> parameter.
	 * 
	 * @param cause the cause of the exception
	 */
	public TreeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Create an exception with the specified <code>message</code> and
	 * <code>cause</code>. In this case, the exception will have the detail
	 * message set by <code>message</code> parameter and <code>cause</code> as
	 * the {@link #initCause(Throwable)}.
	 * 
	 * @param message the detail message of the exception
	 * 
	 * @param cause the cause of the exception 
	 */
	public TreeException(String message, Throwable cause) {
		super(message, cause);
	}
}
