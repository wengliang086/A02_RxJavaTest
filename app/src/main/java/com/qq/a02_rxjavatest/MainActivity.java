package com.qq.a02_rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;
import rx.functions.Func3;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
            }
        };
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "onCompletedAction");
            }
        };

        String[] words = {"Hellow", "Hi", "Aloha"};
        Observable observable = Observable.from(words);
        observable.subscribe(onNextAction);
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

        // map 一对一转换
        Observable.just("aaa", "bbb", "ccc")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "123-" + s;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });
        // flatmap 一对多转换
        Observable.from(new String[]{"aaa", "bbb", "ccc"})
                .flatMap(new Func1<String, Observable<Character>>() {
                    @Override
                    public Observable<Character> call(String s) {
                        Character[] as = new Character[s.length()];
                        for (int i = 0; i < s.length(); i++) {
                            as[i] = s.charAt(i);
                        }
                        return Observable.from(as);
                    }
                })
                .subscribe(new Action1<Character>() {
                    @Override
                    public void call(Character character) {
                        Log.i(TAG, "character " + character);
                    }
                });
    }
}
