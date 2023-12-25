package search.searchStrategy;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchAny extends SearchTemplate {
    public SearchAny(Map<String, Set<Integer>> peoples, List<String> listOfPeople) {
        super(peoples, listOfPeople);
    }

    @Override
    public void search(List<Integer> matchedDocumentIds) {
        if (matchedDocumentIds != null) {
            for (Integer index : matchedDocumentIds) {
                foundPeople.add(listOfPeople.get(index));
            }
        }
    }
}
