package model;

import java.util.List;
import java.util.stream.Collectors;

public record AgricultureNode(String label, int level, List<AgricultureNode> children) {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.repeat(" - ", level);
        stringBuilder.append(label);
        stringBuilder.append(children.stream()
                .map(child -> "\n" + child.toString())
                .collect(Collectors.joining()));
        return stringBuilder.toString();
    }
}
