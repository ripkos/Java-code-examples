package zad1.models;

public class Topic {
    public String name;


    public String getName() {
        return name;
    }

    public Topic(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Topic) {
            return name.equals(((Topic) obj).name);
        }
        return super.equals(obj);
    }

}
