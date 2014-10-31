package rest.items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 31/10/14.
 */
public class Act {
    private final static TimeZone tz_gmt = TimeZone.getTimeZone("GMT");
    private final static Pattern location_pattern = Pattern.compile("@(\\w+)");
    private final static Pattern number_pattern = Pattern.compile("\\+(\\d+)");

    private String raw;
    private long Id;
    private int duration = 120;

    private Calendar createdAt = new GregorianCalendar(tz_gmt);
    private Calendar datetime = new GregorianCalendar(tz_gmt);

    private long creatorId;
    private boolean isPublic;
    private ArrayList<String> tags = new ArrayList<String>();
    private String location;


    /**
     * Event factory
     *
     * @param creator
     * @param raw
     * @return
     */
    public static Act fromString(long creator, String raw) {
        Act e = new Act();
        e.setRaw(raw);
        e.setCreatorId(creator);


        //use natty to parse date
        /*
        Parser parser = new Parser(tz_cet);

        List<DateGroup> groups = parser.parse(raw);

        for(DateGroup group:groups) {
            List<Date> dates = group.getDates();
            e.datetime.setTime(dates.get(0));
        }
        */

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
            long user = Long.parseLong(number, 10);
            //e.invite(user, creator);
        }

        if(e.tags.contains("public"))
            e.isPublic = true;

        return e;
    }

    public static Act demoAct(){
        return Act.fromString(4369911602033L, "some demo #act with +004369911602033");
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
}
