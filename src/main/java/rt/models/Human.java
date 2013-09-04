package rt.models;

/**
 * User: Andrew.Nazymko
 */
public class Human {
    String name;
    Integer id;
    Integer unread = 0;

    public Human() {
    }

    public Human(String name, Integer id, Integer unread) {
        this.name = name;
        this.id = id;
        this.unread = unread;
    }

    public Human(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        if (id != null ? !id.equals(human.id) : human.id != null) return false;
        if (name != null ? !name.equals(human.name) : human.name != null) return false;
        if (unread != null ? !unread.equals(human.unread) : human.unread != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (unread != null ? unread.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + (unread > 0 ? "(" + unread + ")" : "");
    }
}
