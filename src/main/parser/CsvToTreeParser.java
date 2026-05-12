package parser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import model.AgricultureNode;

public class CsvToTreeParser {
    private final RowToNodeParser lineToNodeParser = new RowToNodeParser("\"", ";");

    public AgricultureNode parseFileContent(List<String> lines) {
        AgricultureNode rootNode = new AgricultureNode(null, 0, new ArrayList<>());
        Deque<AgricultureNode> nodeStack = new ArrayDeque<>();
        nodeStack.add(rootNode);
        for (int i = 1; i < lines.size(); i++) {
            AgricultureNode thisNode = lineToNodeParser.parseLine(lines.get(i));
            if (previousNode(nodeStack).level() < thisNode.level() - 1) {
                throw new IllegalStateException();
            }
            while (previousNode(nodeStack).level() >= thisNode.level()) {
                nodeStack.removeLast();
            }
            previousNode(nodeStack).children().add(thisNode);
            nodeStack.addLast(thisNode);
        }
        return rootNode;
    }

    private static AgricultureNode previousNode(Deque<AgricultureNode> nodeStack) {
        if (nodeStack.isEmpty()) {
            throw new IllegalStateException();
        }
        return nodeStack.peekLast();
    }
}
