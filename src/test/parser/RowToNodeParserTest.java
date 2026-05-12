package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import model.AgricultureNode;

class RowToNodeParserTest {

    private final RowToNodeParser rowToNodeParser = new RowToNodeParser("\"", ";");

    @ParameterizedTest
    @CsvSource(value = {
            "A;;;;;AGRICULTURE, FORESTRY AND FISHING;;;;;;;;;;;;;;|                                             1|  AGRICULTURE, FORESTRY AND FISHING",
            ";01;;;;Crop and animal production, hunting and related service activities;;;;;;;;;;;;;;|           2|  Crop and animal production, hunting and related service activities",
            ";;01.1;;;Growing of non-perennial crops;;;;;;;;;;;;;;|                                             3|  Growing of non-perennial crops",
            ";;01.1;;;;;;Growing of non-perennial crops;;;;;;;;;;;|                                             3|  Growing of non-perennial crops",
            ";;;08.12;0810*;\"Operation of gravel and sand pits; mining of clays and kaolin\";;;;;;;;;;;;;;|    4|  Operation of gravel and sand pits; mining of clays and kaolin",
    }, delimiter = '|')
    void testParseLine(String line, int expectedLevel, String expectedLabel) {
        AgricultureNode actual = rowToNodeParser.parseLine(line);
        AgricultureNode expected = new AgricultureNode(expectedLabel, expectedLevel, List.of());
        assertEquals(expected, actual);
    }

    @Test
    void testSplitIntoColumnsWhenDelimiterInLabel() {
        String line = ";;;08.12;0810*;\"Operation of gravel and sand pits; mining of clays and kaolin\";;;;;;;;;;;;;;";
        List<String> actual = rowToNodeParser.splitIntoColumns(line);
        List<String> expected = List.of("", "", "",
                "08.12", "0810*", "Operation of gravel and sand pits; mining of clays and kaolin"
        );
        assertEquals(expected, actual);
    }

    @Test
    void testSplitIntoColumnsWhenDelimiterInAnyColumn() {
        String line = "\"a column; with delimiter\";;a column without strings;\"a column without delimiter\";;\"a column; with multiple; delimiters\";";
        List<String> expected = List.of(
                "a column; with delimiter",
                "",
                "a column without strings",
                "a column without delimiter",
                "",
                "a column; with multiple; delimiters"
        );
        assertEquals(expected, rowToNodeParser.splitIntoColumns(line));
    }
}