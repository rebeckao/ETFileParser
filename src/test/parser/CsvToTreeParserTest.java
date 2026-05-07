package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

import model.AgricultureNode;

class CsvToTreeParserTest {

    private final CsvToTreeParser csvToTreeParser = new CsvToTreeParser();

    @Test
    void testParseFileContent() {
        List<String> fileContent = List.of(
                "Section;Division;Group;Class;ISIC Rev. 4;Label;",
                "section-1;;;;;Section 1;",
                ";division-1;;;;Division 1;",
                ";;group-1;;;Group 1;",
                ";;;class-1;;Class 1;",
                ";;;class-2;;Class 2;",
                ";division-2;;;;Division 2;",
                ";;group-2;;;Group 2;",
                ";;;class-3;;Class 3;",
                ";;group-3;;;Group 3;",
                "section-2;;;;;Section 2;",
                ";division-3;;;;\"Division 3; with delimiter\";",
                ";;group-4;;;Group 4;",
                ";;;class-4;;Class 4;"
        );
        AgricultureNode actual = csvToTreeParser.parseFileContent(fileContent);
        AgricultureNode expected = new AgricultureNode(null, 0, List.of(
                new AgricultureNode("Section 1", 1, List.of(
                        new AgricultureNode("Division 1", 2, List.of(
                                new AgricultureNode("Group 1", 3, List.of(
                                        new AgricultureNode("Class 1", 4, List.of()),
                                        new AgricultureNode("Class 2", 4, List.of())
                                ))
                        )),
                        new AgricultureNode("Division 2", 2, List.of(
                                new AgricultureNode("Group 2", 3, List.of(
                                        new AgricultureNode("Class 3", 4, List.of())
                                )),
                                new AgricultureNode("Group 3", 3, List.of())
                        ))
                )),
                new AgricultureNode("Section 2", 1, List.of(
                        new AgricultureNode("Division 3; with delimiter", 2, List.of(
                                new AgricultureNode("Group 4", 3, List.of(
                                        new AgricultureNode("Class 4", 4, List.of())
                                ))
                        ))
                ))
        ));
        assertEquals(expected, actual);
    }
}