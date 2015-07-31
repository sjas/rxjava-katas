package com.senacor.tecco.codecamp.reactive.services;

import com.bitplan.mediawiki.japi.Mediawiki;
import rx.Observable;

import static com.senacor.tecco.codecamp.reactive.ReactiveUtil.getThreadId;

/**
 * @author Andreas Keefer
 */
public class WikipediaServiceJapi {

    private final Mediawiki wiki;

    public WikipediaServiceJapi() {
        try {
            wiki = new Mediawiki("http://de.wikipedia.org");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        WikipediaServiceJapi wikipediaService = new WikipediaServiceJapi();
        String article = wikipediaService.getArticle("42");
        System.out.println("article=" + article);
    }

    public String getArticle(String name) {
        try {
            final long start = System.currentTimeMillis();
            String res = wiki.getPageContent(name);
            System.out.println(getThreadId() +
                    "profiling getArticle(" + name + "): " + (System.currentTimeMillis() - start) + "ms");
            return res;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Observable<String> getArticleObservable(String wikiArticle) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(getArticle(wikiArticle));
                subscriber.onCompleted();
            } catch (RuntimeException e) {
                subscriber.onError(e);
            }
        });
    }
}
