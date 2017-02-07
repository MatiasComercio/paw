package ar.edu.itba.paw.webapp.models;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

  @Override
  public LocalDateTime unmarshal(String localDateTime) throws Exception {
    //return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    return LocalDateTime.parse(localDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  }

  @Override
  public String marshal(LocalDateTime localDateTime) throws Exception {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime);
  }
}
