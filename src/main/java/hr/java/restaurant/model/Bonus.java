package hr.java.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;

public record Bonus(BigDecimal bonus) implements Serializable {
    public Bonus {
    }
}
