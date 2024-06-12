package Forms;

import Errors.InvalidFormException;

public abstract class Form<T> {
    public abstract T build() throws InvalidFormException;
}
