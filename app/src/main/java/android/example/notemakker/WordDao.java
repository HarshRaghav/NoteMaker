package android.example.notemakker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("UPDATE word_table SET word= :str WHERE id = :wid")
    void update(String str, int wid);

    @Delete
    void delete(Word word);

    @Query("SELECT * FROM word_table ORDER BY id ASC")
    LiveData<List<Word>> getAllWords();
}
