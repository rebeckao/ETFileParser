import model.AgricultureNode;
import service.TreeService;

void main() {
    TreeService treeService = new TreeService("input.csv");
    treeService.printTree();
    List<AgricultureNode> foundNodes = treeService.searchNode("Activities of extraterritorial organisations and bodies");
    for (AgricultureNode foundNode: foundNodes) {
        IO.println("Found node:");
        treeService.printTree(foundNode);
    }

}
