package android.example.notemakker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements onClicked{
    private WordViewModel wordViewModel;
    private WordAdapter wordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recylerview);
        wordAdapter=new WordAdapter(new WordAdapter.WordDiff(),this);
        rv.setAdapter(wordAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WordViewModel.class);
        wordViewModel.AllWords.observe(this , new Observer<List<Word>>(){
            @Override
            public void onChanged(List<Word> words) {
                wordAdapter.update(words);
            }
        });
    }

    public void save(View view){
        EditText editText=(EditText)findViewById(R.id.input);
        String str = editText.getText().toString();
                if(!TextUtils.isEmpty(str)){
                    wordViewModel.insert(new Word(str));
                }
                Toast.makeText(this,"Text Saved",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClicking(Word word) {
        wordViewModel.delete(word);
        Toast.makeText(this,word.getWord()+" 'deleted'",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SaveUpdate(Word word, String updatedString) {

        wordViewModel.update(new Word(updatedString),word.getId());
    }
}