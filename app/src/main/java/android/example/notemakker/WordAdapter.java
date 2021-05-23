package android.example.notemakker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ListAdapter<Word,WordViewHolder> {
    private List<Word> updateUI=new ArrayList<>();
    private onClicked onclicked;

    static class WordDiff extends DiffUtil.ItemCallback<Word> {
        @Override
        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }
    }

    protected WordAdapter(@NonNull DiffUtil.ItemCallback<Word> diffCallback,onClicked onclicked) {
        super(diffCallback);
        this.onclicked=onclicked;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        WordViewHolder wordViewHolder = new WordViewHolder(view);
        wordViewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicked.onClicking(updateUI.get(wordViewHolder.getAdapterPosition()));
            }
        });

        wordViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wordViewHolder.editText.isShown()){
                    wordViewHolder.editText.setVisibility(View.GONE);
                    wordViewHolder.saveChanges.setVisibility(View.GONE);
                }
                else{
                    wordViewHolder.editText.setVisibility(View.VISIBLE);
                    wordViewHolder.saveChanges.setVisibility(View.VISIBLE);
                }
            }
        });

        wordViewHolder.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String up=wordViewHolder.editText.getText().toString();
                if(!up.isEmpty()){
                    onclicked.SaveUpdate(updateUI.get(wordViewHolder.getAdapterPosition()) , up);
                }
            }
        });
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word current =updateUI.get(position);
        holder.savedText.setText(current.getWord());
    }

    public void update(List<Word> N){
        updateUI.clear();
        updateUI.addAll(N);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return updateUI.size();
    }
}
class WordViewHolder extends RecyclerView.ViewHolder{
    public WordViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    TextView savedText = (TextView)itemView.findViewById(R.id.saved_text);
    ImageView del = (ImageView)itemView.findViewById(R.id.del);
    ImageView edit = (ImageView)itemView.findViewById(R.id.edit_text);
    ImageView saveChanges = (ImageView)itemView.findViewById(R.id.save_changes);
    EditText editText = (EditText)itemView.findViewById(R.id.changes);
}
interface onClicked{
    void SaveUpdate(Word word,String updatedString);
    void onClicking(Word word);
}