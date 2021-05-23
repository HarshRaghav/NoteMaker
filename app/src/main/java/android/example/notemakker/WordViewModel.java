package android.example.notemakker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    public LiveData<List<Word>> AllWords;
    public WordRepository wordRepository;

    public WordViewModel(Application application){
        super(application);
        wordRepository =new WordRepository(application);
        this.AllWords=wordRepository.getAllWords();
    }
    public void delete(Word word){
        wordRepository.delete(word);
    }
    public void insert(Word word){
        wordRepository.insert(word);
    }
    public void update(Word word , int id) {wordRepository.update(word,id);
    }
}
