package kahveci.purchase.domain;

public interface DiscountRule {
    double apply(Cart cart);
}
