package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AgricultureNode;
import parser.CsvToTreeParser;

public class TreeService {
    private final AgricultureNode rootNode;
    private final Map<String, List<AgricultureNode>> nodeSearchMap;

    public TreeService(String fileName) {
        rootNode = readTree(fileName);
        nodeSearchMap = buildNodeSearchMap(rootNode);
    }

    private AgricultureNode readTree(String fileName) {
        try {
            return new CsvToTreeParser().parseFileContent(Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void printTree(AgricultureNode agricultureNode) {
        IO.println(agricultureNode);
    }

    public void printTree() {
        printTree(rootNode);
    }

    public List<AgricultureNode> searchNode(String label) {
        return nodeSearchMap.getOrDefault(label, List.of());
    }

    private Map<String, List<AgricultureNode>> buildNodeSearchMap(AgricultureNode root) {
        Map<String, List<AgricultureNode>> map = new HashMap<>();
        addNodeToMap(root, map);
        return map;
    }

    private void addNodeToMap(AgricultureNode node, Map<String, List<AgricultureNode>> nodeSearchMap) {
        nodeSearchMap.putIfAbsent(node.label(), new ArrayList<>());
        nodeSearchMap.get(node.label()).add(node);
        for (AgricultureNode child : node.children()) {
            addNodeToMap(child, nodeSearchMap);
        }
    }
}
