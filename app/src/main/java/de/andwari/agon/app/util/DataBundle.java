package de.andwari.agon.app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataBundle {

    private final Map<String, Object> map;

    private DataBundle() {
        map = new HashMap<>();
    }

    public static DataBundle empty() {
        return new DataBundle();
    }

    public void addData(String key, Object data) {
        map.put(key, data);
    }

    public Object getData(String key) {
        return map.get(key);
    }

}
