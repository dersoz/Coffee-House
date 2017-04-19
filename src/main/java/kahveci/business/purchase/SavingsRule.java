package kahveci.business.purchase;

import kahveci.domain.Cart;

public interface SavingsRule {
    double apply(Cart cart);
}
