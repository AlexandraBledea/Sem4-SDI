package busManagement.domain;

import java.util.Objects;

public class BusCompany extends BaseEntity<Long> {

    private String name;

    public BusCompany(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BusCompany busCompany = (BusCompany) obj;
        return Objects.equals(name, busCompany.name);
    }

    @Override
    public String toString() {
        return "BusCompany{" +
                "name='" + name + '\'' +
                '}' + super.toString();
    }
}
