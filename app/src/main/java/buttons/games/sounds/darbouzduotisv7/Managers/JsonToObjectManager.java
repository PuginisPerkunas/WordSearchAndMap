package buttons.games.sounds.darbouzduotisv7.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import buttons.games.sounds.darbouzduotisv7.Models.DefinitionExampleModel;
import buttons.games.sounds.darbouzduotisv7.Models.ExamplesModel;

public class JsonToObjectManager {

    private Gson gsonWorker;
    private String json;


    public JsonToObjectManager(String json) {
        this.json = json;
    }

    public DefinitionExampleModel getDefinitionObject() {
        gsonWorker = new GsonBuilder().create();
        return gsonWorker.fromJson(json, DefinitionExampleModel.class);
    }

    public ExamplesModel getExamplesObject() {
        gsonWorker = new  GsonBuilder().create();
        return gsonWorker.fromJson(json, ExamplesModel.class);
    }


}
