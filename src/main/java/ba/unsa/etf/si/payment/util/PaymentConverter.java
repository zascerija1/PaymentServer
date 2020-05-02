package ba.unsa.etf.si.payment.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PaymentConverter  implements AttributeConverter<PaymentStatus, String> {
    @Override
    public String convertToDatabaseColumn(PaymentStatus notificationStatus) {
        if (notificationStatus == null) {
            return null;
        }
        return notificationStatus.getStatus();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PaymentStatus.values())
                .filter(c -> c.getStatus().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}