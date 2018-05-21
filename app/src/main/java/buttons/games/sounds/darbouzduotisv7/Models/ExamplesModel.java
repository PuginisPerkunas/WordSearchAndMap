package buttons.games.sounds.darbouzduotisv7.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamplesModel implements Parcelable {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("examples")
    @Expose
    private List<String> examples = null;

    public ExamplesModel() {
    }

    protected ExamplesModel(Parcel in) {
        word = in.readString();
        examples = in.createStringArrayList();
    }

    public static final Creator<ExamplesModel> CREATOR = new Creator<ExamplesModel>() {
        @Override
        public ExamplesModel createFromParcel(Parcel in) {
            return new ExamplesModel(in);
        }

        @Override
        public ExamplesModel[] newArray(int size) {
            return new ExamplesModel[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeStringList(examples);
    }
}
