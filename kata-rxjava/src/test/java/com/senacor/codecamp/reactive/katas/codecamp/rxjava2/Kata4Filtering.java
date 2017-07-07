package com.senacor.codecamp.reactive.katas.codecamp.rxjava2;

import com.senacor.codecamp.reactive.services.WikiService;
import com.senacor.codecamp.reactive.katas.KataClassification;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.senacor.codecamp.reactive.katas.KataClassification.Classification.advanced;
import static com.senacor.codecamp.reactive.katas.KataClassification.Classification.hardcore;
import static org.eclipse.persistence.internal.expressions.SpatialExpressionOperators.filter;

/**
 * @author Andreas Keefer
 */
public class Kata4Filtering {

    private final WikiService wikiService = WikiService.create();

    @Test
    @KataClassification(advanced)
    public void filterObservable() throws Exception {
        // 1. Use WikiService#wikiArticleBeingReadObservable that delivers a stream of WikiArticle names being read
        wikiService.wikiArticleBeingReadObservable(1, TimeUnit.MILLISECONDS)
        // 2. Filter the names so that only articles with at least 15 characters long names are accepted and print everything to the console
            .filter(name -> name.length() >= 15)
            .subscribe(next -> System.out.print(next))    ;

    }

    @Test
    @KataClassification(hardcore)
    public void filterObservable2() throws Exception {
        // 1. Use WikiService#wikiArticleBeingReadObservable that delivers a stream of WikiArticle names being read
        // 2. The stream delivers to many article to be processed.
        //    Limit the stream to one article in 500ms. Do not change the parameter at wikiArticleBeingReadObservable ;)

        wikiService.wikiArticleBeingReadObservable(100, TimeUnit.MILLISECONDS);
    }
}
