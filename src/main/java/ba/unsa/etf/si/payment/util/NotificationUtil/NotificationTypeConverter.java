package ba.unsa.etf.si.payment.util.NotificationUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationTypeConverter  implements AttributeConverter<NotificationType, String> {
    @Override
    public String convertToDatabaseColumn(NotificationType notificationType) {
        if (notificationType == null) {
            return null;
        }
        return notificationType.getType();
    }

    @Override
    public NotificationType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(NotificationType.values())
                .filter(c -> c.getType().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}