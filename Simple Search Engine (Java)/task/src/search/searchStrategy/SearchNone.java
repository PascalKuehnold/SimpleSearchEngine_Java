package search.searchStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchNone extends SearchTemplate {
    public SearchNone(Map<String, Set<Integer>> peoples, List<String> listOfPeople) {
        super(peoples, listOfPeople);
    }

    @Override
    public void search(List<Integer> matchedDocumentIds) {
        if (matchedDocumentIds != null) {
            foundPeople = new HashSet<>(listOfPeople);

            for (Integer index : matchedDocumentIds) {
                foundPeople.remove(listOfPeople.get(index));
            }
        }
    }
}
