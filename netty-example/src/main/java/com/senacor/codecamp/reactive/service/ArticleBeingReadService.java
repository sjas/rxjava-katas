package com.senacor.codecamp.reactive.service;

import com.senacor.codecamp.reactive.services.CountService;
import com.senacor.codecamp.reactive.services.RatingService;
import com.senacor.codecamp.reactive.services.WikiService;
import com.senacor.codecamp.reactive.util.DelayFunction;
import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import net.sourceforge.jwbf.core.contentRep.Article;
import org.wikipedia.Wiki;

import java.util.concurrent.TimeUnit;


/**
 * @author Andri Bremm
 */
public class ArticleBeingReadService {

    private WikiService wikiService;
    private RatingService ratingService;
    private CountService countService;

    public ArticleBeingReadService() {
        this(WikiService.create(DelayFunction.staticDelay(30)), RatingService.create(), CountService.create());
    }

    public ArticleBeingReadService(WikiService wikiService, RatingService ratingService, CountService countService) {
        this.wikiService = wikiService;
        this.ratingService = ratingService;
        this.countService = countService;
    }

    public Observable<WikiArticle> articleBeingReadObservable() {
        return wikiService.wikiArticleBeingReadObservableBurst()
                // or return wikiService.wikiArticleBeingReadObservable(100, TimeUnit.MILLISECONDS)
                .flatMap(this::createArticle);
    }

    public Observable<WikiArticle> createArticle(final String articleName) {
        /* Tasks:
         * 1. fetch the article using wikiService.fetchArticleObservable
         * Hint: there is an existing Test (ArticleBeingReadServiceTest) which is ignored. Remove the @Ignored
         *       Annotation and implement all 3 Tasks Test-Driven.
         * 2. parse the article using wikiService::parseMediaWikiTextObservable
         * 3. calculate a rating (ratingService.rate) and the word count (countService.countWords) for the article. Store the results in a WikiArticle object
         */
        return Observable.just(articleName)
                .flatMap(wikiService::fetchArticleObservable)
                .flatMap(wikiService::parseMediaWikiTextObservable)
                .flatMap(parsedPage ->
                    ratingService.rateObservable(parsedPage)
                            .zipWith(countService.countWordsObservable(parsedPage),
                                    (rating, wordcount) ->
                                            new WikiArticle(articleName, getArticleShortText(parsedPage), rating, wordcount)));
    }

    private String getArticleShortText(ParsedPage parsedPage) {
        if (parsedPage.getText().length() < 100) {
            return parsedPage.getText();
        } else {
            return parsedPage.getText().substring(0, 99) + "...";
        }

    }
}
