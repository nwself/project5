package edu.cs5774.project5;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Calendar> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

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