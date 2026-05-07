package parser;

import java.util.ArrayList;
import java.util.List;

import model.AgricultureNode;

public class RowToNodeParser {
    private final int labelColumn;
    private final String quote;
    private final String delimiter;

    RowToNodeParser(int labelColumn, String quote, String delimiter) {
        this.labelColumn = labelColumn;
        this.quote = quote;
        this.delimiter = delimiter;
    }

    AgricultureNode parseLine(String line) {
        List<String> columns = splitIntoColumns(line);
        if (columns.size() < labelColumn - 1) {
            throw new IllegalStateException();
        }
        String label = columns.get(labelColumn);
        int level = resolveLevel(columns);
        return new AgricultureNode(label, level, new ArrayList<>());
    }

    List<String> splitIntoColumns(String line) {
        List<String> columns = new ArrayList<>();
        String restOfString = line;
        while (!restOfString.isEmpty()) {
            if (restOfString.startsWith(quote)) {
                int closingQuoteIndex = restOfString.indexOf(quote, 1);
                columns.add(restOfString.substring(1, closingQuoteIndex));
                if (!restOfString.startsWith(delimiter, closingQuoteIndex + 1)) {
                    throw new IllegalStateException();
                }
                restOfString = restOfString.substring(closingQuoteIndex + 2);
            } else {
                int nextDelimiterIndex = restOfString.indexOf(delimiter);
                columns.add(restOfString.substring(0, nextDelimiterIndex));
                restOfString = restOfString.substring(nextDelimiterIndex + 1);
            }
        }
        return columns;
    }

    private int resolveLevel(List<String> columns) {
        int level = 1;
        while (columns.get(level - 1).isEmpty()) {
            level++;
            if (level >= columns.size()) {
                throw new IllegalStateException();
            }
        }
        return level;
    }
}
