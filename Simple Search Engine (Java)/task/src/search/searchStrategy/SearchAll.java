package search.searchStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchAll extends SearchTemplate {
    public SearchAll(Map<String, Set<Integer>> peoples, List<String> listOfPeople) {
        super(peoples, listOfPeople);
    }

    @Override
    public void search(List<Integer> matchedDocumentIds) {
        String[] searchTerms = searchTerm.split(" ");
        HashMap<Integer, Integer> sorted = new HashMap<>();

        for (Integer index: matchedDocumentIds){
            sorted.merge(index, 1, Integer::sum);
        }

        for (Map.Entry<Integer, Integer> entry: sorted.entrySet()){
            if(entry.getValue() >= searchTerms.length){
               foundPeople.add(listOfPeople.get(entry.getKey()));
            }
        }
    }
}
