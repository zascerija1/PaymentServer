package ba.unsa.etf.si.payment.util.NotificationUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationStatusConverter  implements AttributeConverter<NotificationStatus, String> {
    @Override
    public String convertToDatabaseColumn(NotificationStatus notificationStatus) {
        if (notificationStatus == null) {
            return null;
        }
        return notificationStatus.getStatus();
    }

    @Override
    public NotificationStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(NotificationStatus.values())
                .filter(c -> c.getStatus().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}