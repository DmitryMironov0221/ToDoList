package com.example.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EnterNoteViewModel extends AndroidViewModel {
    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public EnterNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDataBase.getInstance(application).notesDao();

    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void saveNote(Note note){
       Disposable disposable = notesDao.add(note)// disposable служит для того, чтобы управлять жизненным циклом активити
                .subscribeOn(Schedulers.io())//переключение на фоновый поток
                .observeOn(AndroidSchedulers.mainThread())// переключение на главный поток
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("AddNoteViewModel", "subscribe");
                        shouldCloseScreen.postValue(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("EnterNoteViewModel","Error2");
                    }
                });
       compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {// метод который позволяет отменять подписку,
        // подписка обязательна, при использовании javaRX и типа данных Complitable
        super.onCleared();
        compositeDisposable.dispose();
    }
}
