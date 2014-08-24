package com.getbro.api.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chris on 17/08/14.
 */
public class DateAdapter extends XmlAdapter<String, Calendar> {

    //ISO 8601
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    @Override
    public Calendar unmarshal(String v) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(v));
        return cal;
    }

    @Override
    public String marshal(Calendar v) throws Exception {
        return dateFormat.format(v.getTime());
    }
}
