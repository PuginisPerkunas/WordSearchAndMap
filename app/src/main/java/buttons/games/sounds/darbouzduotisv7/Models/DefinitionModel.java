package buttons.games.sounds.darbouzduotisv7.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefinitionModel implements Parcelable {

    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("partOfSpeech")
    @Expose
    private String partOfSpeech;

    public DefinitionModel() {
    }

    protected DefinitionModel(Parcel in) {
        definition = in.readString();
        partOfSpeech = in.readString();
    }

    public static final Creator<DefinitionModel> CREATOR = new Creator<DefinitionModel>() {
        @Override
        public DefinitionModel createFromParcel(Parcel in) {
            return new DefinitionModel(in);
        }

        @Override
        public DefinitionModel[] newArray(int size) {
            return new DefinitionModel[size];
        }
    };

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(definition);
        dest.writeString(partOfSpeech);
    }
}
