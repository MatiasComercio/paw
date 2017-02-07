package ar.edu.itba.paw.webapp.models;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * NOTE: LocalDate is mapped through the LocalDateAdapter,
 * but it does not throw an exception in case the format is no valid, instead it returns a null LocalDate, despite the documentation of LocalDate.parse
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String dateInput) throws Exception {
        return LocalDate.parse(dateInput, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return DateTimeFormatter.ISO_DATE.format(localDate);
    }
}