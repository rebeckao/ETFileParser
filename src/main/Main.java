import model.AgricultureNode;
import parser.CsvToTreeParser;

void main() throws IOException {
    List<String> lines = Files.readAllLines(Path.of("input.csv"));
    AgricultureNode rootNode = new CsvToTreeParser().parseFileContent(lines);
    IO.println(rootNode);
}
