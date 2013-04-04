package eu.elderspaces.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class Event extends Entity {
    
    private String shortDescription;
    private String name;
    
    public Event() {
    
    }
    
    public Event(final String id, final String name, final String shortDescription) {
    
        super(id);
        this.shortDescription = shortDescription;
        this.name = name;
    }
    
    public String getShortDescription() {
    
        return shortDescription;
    }
    
    public void setShortDescription(final String shortDescription) {
    
        this.shortDescription = shortDescription;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName(final String name) {
    
        this.name = name;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Event that = (Event) o;
        
        return Objects.equal(shortDescription, that.shortDescription)
                && Objects.equal(name, that.name);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(shortDescription, name);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(shortDescription).addValue(name).toString();
    }
    
}
