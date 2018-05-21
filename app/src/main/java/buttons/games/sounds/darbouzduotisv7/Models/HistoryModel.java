package buttons.games.sounds.darbouzduotisv7.Models;

public class HistoryModel {

    private String word, partOfSpeech, dateSearch;

    public HistoryModel(String word, String partOfSpeech, String dateSearch) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.dateSearch = dateSearch;
    }

    public String getWord() {
        return word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getDateSearch() {
        return dateSearch;
    }
}
