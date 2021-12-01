package de.andwari.agon.app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataBundle {

    private final Map<String, Data> map;

    private DataBundle() {
        map = new HashMap<>();
    }

    public static DataBundle empty() {
        return new DataBundle();
    }

    public void addData(String key, Data data) {
        map.put(key, data);
    }

    public Optional<Data> getData(String key) {
        return Optional.ofNullable(map.get(key));
    }
}
