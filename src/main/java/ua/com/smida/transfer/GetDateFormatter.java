package ua.com.smida.transfer;

import java.time.format.DateTimeFormatter;

class GetDateFormatter {

    static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
}
