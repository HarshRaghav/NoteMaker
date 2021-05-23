package android.example.notemakker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version =1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
    private static volatile WordDatabase INSTANCE;
    private static final int FIXED_THREADS = 4;

    static final ExecutorService databaseWriter = Executors.newFixedThreadPool(FIXED_THREADS);

    static WordDatabase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (WordDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_databsae").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriter.execute(() -> {
                WordDao dao = INSTANCE.wordDao();
                Word word = new Word("Hello");
                dao.insert(word);
                dao.update(word.getWord(),word.getId());
                dao.delete(word);
            });
        }
    };
}
