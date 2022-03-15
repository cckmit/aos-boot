package ink.aos.boot.db.converter;

import javax.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Double, Long> {

    @Override
    public Long convertToDatabaseColumn(Double d) {
        if (d == null) return null;
        return Math.round(d * 100);
    }

    @Override
    public Double convertToEntityAttribute(Long l) {
        if (l == null) return null;
        return l.doubleValue() / 100;
//        BigDecimal a = new BigDecimal(l);
//        return a.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

//    public static void main(String[] args) {
//        MoneyConverter m = new MoneyConverter();
//
//        System.out.println(m.convertToEntityAttribute(0L));
//        System.out.println(m.convertToEntityAttribute(4L));
//        System.out.println(m.convertToEntityAttribute(5L));
//        System.out.println(m.convertToEntityAttribute(9L));
//        System.out.println(m.convertToEntityAttribute(10L));
//        System.out.println(m.convertToEntityAttribute(14L));
//        System.out.println(m.convertToEntityAttribute(15L));
//        System.out.println(m.convertToEntityAttribute(19L));
//        System.out.println(m.convertToEntityAttribute(994L));
//        System.out.println(m.convertToEntityAttribute(995L));
//        System.out.println(m.convertToEntityAttribute(900000000L));
//    }
}
