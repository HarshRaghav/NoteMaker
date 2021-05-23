package android.example.notemakker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> AllWords;

    WordRepository(Application application){
        WordDatabase wordDatabase = WordDatabase.getInstance(application);
        wordDao=wordDatabase.wordDao();
        AllWords = wordDao.getAllWords();
    }

    void insert(Word word){
        WordDatabase.databaseWriter.execute(() -> {
            wordDao.insert(word);
        });
    }

    void update(Word word,int id){
        WordDatabase.databaseWriter.execute(() -> {
            wordDao.update(word.getWord(),id);
        });
    }

    void delete(Word word){
        WordDatabase.databaseWriter.execute(() -> {
            wordDao.delete(word);
        });
    }

    LiveData<List<Word>> getAllWords(){
        return AllWords;
    }
}
