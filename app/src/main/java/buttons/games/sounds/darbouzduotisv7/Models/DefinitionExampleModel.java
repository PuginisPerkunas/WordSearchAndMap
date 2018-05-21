package buttons.games.sounds.darbouzduotisv7.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefinitionExampleModel implements Parcelable {


    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("definitions")
    @Expose
    private List<DefinitionModel> definitions = null;

    public DefinitionExampleModel() {
    }


    protected DefinitionExampleModel(Parcel in) {
        word = in.readString();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<DefinitionModel> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<DefinitionModel> definitions) {
        this.definitions = definitions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeList(definitions);
    }

    public static final Creator<DefinitionExampleModel> CREATOR = new Creator<DefinitionExampleModel>() {
        @Override
        public DefinitionExampleModel createFromParcel(Parcel in) {
            return new DefinitionExampleModel(in);
        }

        @Override
        public DefinitionExampleModel[] newArray(int size) {
            return new DefinitionExampleModel[size];
        }
    };
}
