package com.rxjava2.android.samples.ui.operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rxjava2.android.samples.R;
import com.rxjava2.android.samples.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by amitshekhar on 17/12/16.
 */

public class ReplaySubjectExampleActivity extends AppCompatActivity {

    private static final String TAG = ReplaySubjectExampleActivity.class.getSimpleName();
    Button btn;
    TextView textView;
    SubjectObserverBuilder observerBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        btn = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.textView);
        observerBuilder = new SubjectObserverBuilder(TAG, textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork();
            }
        });
    }

    /* ReplaySubject emits to any observer all of the items that were emitted
     * by the source Observable, regardless of when the observer subscribes.
     */
    private boolean flag;
    private void doSomeWork() {
        ReplaySubject<Integer> source = ReplaySubject.create();

        observerBuilder.resetIndentCount();
        source.subscribe(observerBuilder.buildObserver("First"));
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.subscribe(observerBuilder.buildObserver("Second"));
        source.onNext(4);
        source.subscribe(observerBuilder.buildObserver("Third"));
        if (flag) {
            source.onComplete();
        } else {
            source.onError(new Throwable());
        }
        flag = !flag;
        source.subscribe(observerBuilder.buildObserver("Fourth"));
        textView.append("========================");
        textView.append(AppConstant.LINE_SEPARATOR);
        Log.d(TAG,      "========================");
    }
}