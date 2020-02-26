package entities.shampoos;

import entities.labels.BasicLabel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "FS")
public class FiftyShades extends BasicShampoo {
    private static final String BRAND = "Fifty shades";
    private static final BigDecimal PRICE = new BigDecimal("6.69");
    private static final Size SIZE = Size.SMALL;

    public FiftyShades() {
    }

    public FiftyShades(BasicLabel classicLabel) {
        super(BRAND, PRICE, SIZE, classicLabel);
    }
}
