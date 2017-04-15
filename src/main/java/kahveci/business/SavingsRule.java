package kahveci.business;

import kahveci.domain.Cart;

public interface SavingsRule {
    double apply(Cart cart);
}
