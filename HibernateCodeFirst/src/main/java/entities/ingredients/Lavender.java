package entities.ingredients;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "LA")
public class Lavender extends BasicIngredient {
    private static final String NAME = "Lavender";
    private static final BigDecimal PRICE = new BigDecimal("2.00");

    public Lavender() {
        super(NAME, PRICE);
    }

    public static String getNAME() {
        return NAME;
    }

    public static BigDecimal getPRICE() {
        return PRICE;
    }
}
