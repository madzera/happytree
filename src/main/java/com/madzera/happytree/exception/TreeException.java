package com.madzera.happytree.exception;

/**
 * The class <code>TreeException</code> represents a checked exception for the
 * HappyTree API by inheriting the {@link Exception} class.
 * 
 * <p>The <code>TreeException</code> is the only checked exception class that
 * can be thrown by this API. This happens when the following validations are
 * not met:</p>
 * 
 * <ul>
 * 	<li>When there is another session with the same identifier while
 * 	initializing a new session;</li>
 * 
 * 	<li>When the class of the object to be transformed has not been annotated
 * 	with {@code @Tree}, {@code @Id}, and {@code @Parent} when initializing a
 * 	new session;</li>
 * 
 * 	<li>When the annotated attributes {@code @Id} and {@code @Parent} have
 * 	different types;</li>
 * 
 * 	<li>When the transaction has no session selected to work on;</li>
 * 
 * 	<li>When the current session is not active;</li>
 * 
 * 	<li>When there are mismatched parameterized types when invoking operations;
 * 	</li>
 * 
 * 	<li>When there is an attempt to handle an element that does not belong to
 * 	the current session;</li>
 * 
 * 	<li>When there is an attempt to handle the root element of the tree;</li>
 * 
 * 	<li>When the element to be cut/copied/removed is not in the <i>ATTACHED</i>
 * 	state in its lifecycle;</li>
 * 
 * 	<li>When the element to be updated is in the <i>NOT_EXISTED</i> state in its
 * 	lifecycle;</li>
 * 
 * 	<li>When the element to be persisted is not in the <i>NOT_EXISTED</i> state
 * 	in its lifecycle;</li>
 * 
 * 	<li>When there is an <code>Element</code> object with a duplicate
 * 	<code>@Id</code> while trying to insert/update or cut/copy an element
 * 	between trees;</li>
 * </ul>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public class TreeException extends Exception {
	private static final long serialVersionUID = 9097414119963988912L;
	
	/**
	 * Default constructor for this exception. This invocation implies a
	 * <code>null</code> detail message for the exception as well as a
	 * <code>null</code> cause.
	 * 
	 * <p>To initialize a cause, invoke {@link #initCause(Throwable)}.</p>
	 */
	public TreeException() {
		super();
	}
	
	/**
	 * This constructor allows specifying a detail message for the exception
	 * when it is thrown. In this case, the cause of the exception is
	 * <code>null</code>.
	 * 
	 * <p>To initialize a cause, invoke {@link #initCause(Throwable)}.</p>
	 * 
	 * @param message the detail message of the exception
	 */
	public TreeException(String message) {
		super(message);
	}
	
	/**
	 * Creates an exception with the specified <code>cause</code>. In this case,
	 * the message of the exception will be the message associated with the
	 * <code>cause</code> parameter.
	 * 
	 * @param cause the cause of the exception
	 */
	public TreeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Creates an exception with the specified <code>message</code> and
	 * <code>cause</code>. In this case, the exception will have the detail
	 * message set by the <code>message</code> parameter and the
	 * <code>cause</code> parameter as the {@link #initCause(Throwable)}.
	 * 
	 * @param message the detail message of the exception
	 * 
	 * @param cause the cause of the exception
	 */
	public TreeException(String message, Throwable cause) {
		super(message, cause);
	}
}
