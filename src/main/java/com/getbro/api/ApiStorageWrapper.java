package com.getbro.api;

import com.getbro.api.items.Event;
import com.getbro.api.items.User;

import java.util.HashMap;

public class ApiStorageWrapper {
    public static HashMap<Long, User> users = new HashMap<Long, User>();
    public static HashMap<Long, Event> events = new HashMap<Long, Event>();

    public ApiStorageWrapper() {
        if (users.size() == 0) {

            users.put(4369911602033L, new User("Chris", 4369911602033L));
            users.put(436802118976L, new User("Alex", 436802118976L));

            Event e;

            e = Event.fromString(4369911602033L, "me #hunger #essen @vapiano now!");
            events.put(e.getId(), e);
            e = Event.fromString(4369911602033L, "kino heute @apollo 18:00 +chris +diana");
            events.put(e.getId(), e);
            e = Event.fromString(4369911602033L, "some #public event next week @home 20:10 +peter");
            events.put(e.getId(), e);
            e = Event.fromString(436802118976L, "dieses event sollte chris nicht sehen");
            events.put(e.getId(), e);

            e = Event.fromString(436802118976L, "rammelrudel tomorrow @alexgarten");
            e.invite(users.get(4369911602033L), users.get(436802118976L));

            events.put(e.getId(), e);
        }
    }
}