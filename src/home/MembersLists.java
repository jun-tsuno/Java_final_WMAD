package home;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MembersLists {
    private StringProperty id;
    private StringProperty name;

    public MembersLists(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }
}
