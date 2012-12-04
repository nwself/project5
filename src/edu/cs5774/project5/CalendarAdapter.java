package edu.cs5774.project5;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * JAXB XML parsing adapter to convert formatted dates from the RSS feed into Java Calendar objects.
 *
 */
public class CalendarAdapter extends XmlAdapter<String, Calendar> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

    @Override
    public String marshal(Calendar v) throws Exception {
        return dateFormat.format(v.getTime());
    }

    @Override
    public Calendar unmarshal(String v) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(dateFormat.parse(v));
        return cal;
    }

}
