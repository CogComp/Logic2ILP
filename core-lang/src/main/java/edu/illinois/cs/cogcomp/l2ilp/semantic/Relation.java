package edu.illinois.cs.cogcomp.l2ilp.semantic;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by haowu on 9/11/16.
 */
public class Relation {

    private String id;
    private Map<String, List<String>> rel;

    public Relation(String id) {
        this.id = id;
        this.rel = new ConcurrentHashMap<>();
    }

    public List<String> maps(String from) {
        return rel.get(from);
    }

    public void def(String from, List<String> to) {
        rel.put(from, to);
    }
}
