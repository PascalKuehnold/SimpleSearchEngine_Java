package search.searchStrategy;

import java.util.*;

abstract class SearchTemplate {
    protected Map<String, Set<Integer>> peoples;
    protected List<String> listOfPeople;
    protected Set<String> foundPeople;

    protected String searchTerm;

    // Initialize the set of document IDs that match all search terms
    List<Integer> matchedDocumentIds = new ArrayList<>();

    public SearchTemplate(Map<String, Set<Integer>> peoples, List<String> listOfPeople) {
        this.peoples = peoples;
        this.listOfPeople = listOfPeople;
        this.foundPeople = new HashSet<>();
    }

    public void startSearch(String searchTerm){
        this.searchTerm = searchTerm;

        initializeMatchedDocumentsIds(searchTerm);
        search(matchedDocumentIds);
        printFoundPeople();
    }

    abstract void search(List<Integer> matchedDocumentIds);

    public void printFoundPeople() {
        // Print the found people
        if (foundPeople.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(foundPeople.size() + " peoples found:");

            for (String person : foundPeople) {
                System.out.println(person);
            }
        }
    }

    private void initializeMatchedDocumentsIds(String _searchTerm) {
        // Split the search term into individual words
        String[] searchTerms = _searchTerm.toLowerCase().split(" ");

        // Iterate through each search term
        for (String searchTerm : searchTerms) {
            // Retrieve the set of document IDs associated with the search term from the inverted index
            Set<Integer> documentIds = null;

            if(peoples.containsKey(searchTerm)){
                documentIds = peoples.get(searchTerm);
            }

            // Reduce the set of matched document IDs to only those that include all search terms
            if(documentIds != null){
                matchedDocumentIds.addAll(documentIds);
            }
        }
    }
}