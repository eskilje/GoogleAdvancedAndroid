package com.example.eliasskilje.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert (Word word) {
        new insertAsyncTask(wordDao).execute(word);
    }

    public void deleteWord (Word word) {
        new deleteWordAsyncTask(wordDao).execute(word);
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(wordDao).execute();
    }

    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao aSyncWordDao;
        insertAsyncTask(WordDao dao) {
            aSyncWordDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            aSyncWordDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
