package ca.rrc.etc;

/**
 * This generic class stores each customer information.
 */
public class CustomerInfo<T> {

	private T element;

	public CustomerInfo(T element) {
		this.element = element;
	}

	public T getElement() {
		return element;
	}

	public String toString() {
		return element.toString();
	}
}// end of class
