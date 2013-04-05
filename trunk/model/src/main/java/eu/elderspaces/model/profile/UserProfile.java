package eu.elderspaces.model.profile;

import java.util.List;

import com.google.common.base.Objects;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;

public class UserProfile {
    
    private Person user;
    private List<Person> friends;
    private List<Event> events;
    private List<Club> clubs;
    
    public UserProfile() {
    
    }
    
    public UserProfile(final Person user, final List<Person> friends, final List<Event> events,
            final List<Club> clubs) {
    
        this.user = user;
        this.friends = friends;
        this.events = events;
        this.clubs = clubs;
    }
    
    public Person getUser() {
    
        return user;
    }
    
    public void setUser(final Person user) {
    
        this.user = user;
    }
    
    public List<Person> getFriends() {
    
        return friends;
    }
    
    public void setFriends(final List<Person> friends) {
    
        this.friends = friends;
    }
    
    public List<Event> getEvents() {
    
        return events;
    }
    
    public void setEvents(final List<Event> events) {
    
        this.events = events;
    }
    
    public List<Club> getClubs() {
    
        return clubs;
    }
    
    public void setClubs(final List<Club> clubs) {
    
        this.clubs = clubs;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final UserProfile that = (UserProfile) o;
        
        return Objects.equal(user, that.user) && Objects.equal(friends, that.friends)
                && Objects.equal(events, that.events) && Objects.equal(clubs, that.clubs);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(user, friends, events, clubs);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(user).addValue(friends).addValue(events)
                .addValue(clubs).toString();
    }
}
