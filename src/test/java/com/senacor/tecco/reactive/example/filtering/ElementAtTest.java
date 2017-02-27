package com.senacor.tecco.reactive.example.filtering;

import org.junit.Test;
import io.reactivex.Observable;

import static com.senacor.tecco.reactive.ReactiveUtil.print;

/**
 * @author Andreas Keefer
 * @version 2.0
 */
public class ElementAtTest {
    @Test
    public void testElementAt() throws Exception {
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(3)
                .subscribe(next -> print("next: %s", next),
                        Throwable::printStackTrace,
                        () -> print("complete!"));
    }

    @Test
    public void testElementAtOrDefault() throws Exception {
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(99, 100)
                .subscribe(next -> print("next: %s", next),
                        Throwable::printStackTrace);
    }
}
