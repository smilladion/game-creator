package dk.itu.gamecreator.android;

import java.util.HashMap;
import java.util.List;

import dk.itu.gamecreator.android.Components.Component;

// https://www.journaldev.com/9942/android-expandablelistview-example-tutorial
public class ExpandableList {

    public HashMap<Stage, List<Component>> data;

    public ExpandableList(List<Stage> stages) {
        for (Stage s : stages) {
            data.put(s, s.getGameComponents());
        }
    }

    public HashMap<Stage, List<Component>> getData() {
        return data;
    }
}
