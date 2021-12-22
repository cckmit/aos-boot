package ink.aos.boot.db.converter;

import javax.persistence.AttributeConverter;
import java.math.BigDecimal;

public class MoneyConverter implements AttributeConverter<Double, Long> {

    @Override
    public Long convertToDatabaseColumn(Double d) {
        if (d == null) return null;
        return d.longValue() * 100;
    }

    @Override
    public Double convertToEntityAttribute(Long l) {
        if (l == null) return null;
        double d = l / 100.0;
        BigDecimal b = new BigDecimal(d);
        d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }

}
