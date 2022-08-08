package zad1.client.fx;

import javafx.scene.control.CheckBox;
import zad1.models.Topic;

public class CheckBoxWithTopic extends CheckBox {
    public Topic topic;

    public CheckBoxWithTopic(Topic t) {
        super(t.name);
        topic=t;
    }
    @Override
    public String toString() {
        return topic.name;
    }
}
