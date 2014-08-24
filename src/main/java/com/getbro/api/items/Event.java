package com.getbro.api.items;

import com.getbro.api.adapter.DateAdapter;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Comparable<Event> {
    private final static TimeZone tz_cet = TimeZone.getTimeZone("CET");
    private final static TimeZone tz_gmt = TimeZone.getTimeZone("GMT");
    @XmlElement(required=true)
    private String raw;
    @XmlElement(required=true)
    private long eventId;
    //a standard duration of 2h = 120 min
    private int duration = 120;
    private Calendar createdAt = new GregorianCalendar(tz_gmt);
    private long creatorId;
    private boolean isPublic;
    private ArrayList<String> tags = new ArrayList<String>();
    private LinkedList<EventInvitation> invited = new LinkedList<EventInvitation>();

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Calendar datetime = new GregorianCalendar(tz_gmt);
    public static int countID = 0;
    private String location = "somewhere";

    private final static Pattern location_pattern = Pattern.compile("@(\\w+)");
    private final static Pattern number_pattern = Pattern.compile("\\+(\\d+)");
    private final static Logger Log = Logger.getLogger(Event.class.getName());


    /**
     * constructor
     */
    private Event() {
        eventId = countID++;
    }


    /**
     * Event factory
     *
     * @param creator
     * @param raw
     * @return
     */
    public static Event fromString(long creator, String raw) {
        Event e = new Event();
        e.setRaw(raw);
        e.setCreatorId(creator);


        //use natty to parse date
        Parser parser = new Parser(tz_cet);
        List<DateGroup> groups = parser.parse(raw);

        for(DateGroup group:groups) {
            List<Date> dates = group.getDates();
            e.datetime.setTime(dates.get(0));
        }

        //add tags
        Matcher matcher = Pattern.compile("#(\\w+)").matcher(raw);
        while (matcher.find()) {
            e.tags.add(matcher.group(1));
        }

        //add location
        Matcher location_matcher = location_pattern.matcher(raw);
        if (location_matcher.find())
            e.location = location_matcher.group(1);


        //invite people
        Matcher people_matcher = number_pattern.matcher(raw);
        while (people_matcher.find()) {
            String number = people_matcher.group(1).substring(1);
            Log.info("new number found: " + number);
            long user = Long.parseLong(number, 10);
            e.invite(user, creator);
        }

        if(e.tags.contains("public"))
            e.isPublic = true;

        return e;
    }


    public EventInvitation invite(User u, User inviter) {
        EventInvitation i = new EventInvitation(this.getId(), u.getId(), inviter.getId(), InvitationStatus.INVITED);
        invited.add(i);

        return i;
    }

    public EventInvitation invite(long u, long inviter) {
        EventInvitation i = new EventInvitation(this.getId(), u, inviter, InvitationStatus.INVITED);
        invited.add(i);

        return i;
    }



    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    private void addTag(String tag) {
        tags.add(tag);
    }

    public long getTime() {
        return datetime.getTimeInMillis();
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return eventId;
    }

    public long setCreatorId(long id) {
        return creatorId = id;
    }


    public ArrayList<String> getTags() {
        return tags;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public List<Long> getInvitations() {
        List<Long> users = new LinkedList<Long>();

        for (EventInvitation i : invited) {
            users.add(i.getUserId());
        }
        return users;
    }

    public boolean getIsPublic() {
        return isPublic;
    }


    public int getDay() {
        return datetime.get(Calendar.DAY_OF_MONTH);
    }

    public Calendar getDatetime() {
        return datetime;
    }

    @Override
    public int compareTo(Event o) {
        return this.datetime.compareTo(o.datetime);
    }
}