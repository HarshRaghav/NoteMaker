package android.example.notemakker;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {


    @PrimaryKey(autoGenerate = true)private int id;

    @NonNull
    private@ColumnInfo(name = "word")String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
}