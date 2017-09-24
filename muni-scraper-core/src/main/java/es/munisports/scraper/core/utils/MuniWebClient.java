package es.munisports.scraper.core.utils;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by David Munoz on 23/09/2017.
 */
public class MuniWebClient implements Closeable {

    private static final int JAVASCRIPT_BACKGROUND_WAIT_MILLIS = 5_000;
    private static final int INTERVAL_WAIT_MILLIS = 1000;

    private final WebClient webClient;

    public MuniWebClient() {
        webClient = getWebClient();
    }

    public <P extends Page> P fetchPage(String url) throws IOException {
        return webClient.getPage(url);
    }

    public DomElement fetchPageAndGetElementWithId(String url, String idElement) throws IOException {
        return waitUntilLoadedElementWithId((HtmlPage) fetchPage(url), idElement);
    }

    public DomElement clickElementAndGetElementWithId(DomElement domElement, String idElement) throws IOException {
        HtmlPage htmlPage = domElement.click();
        return waitUntilLoadedElementWithId(htmlPage, idElement);
    }

    public DomElement waitUntilLoadedElementWithId(HtmlPage page, String idElement) {
        final int intervals = JAVASCRIPT_BACKGROUND_WAIT_MILLIS / INTERVAL_WAIT_MILLIS;
        for (int i = 0; i < intervals; i++) {
            webClient.waitForBackgroundJavaScript(INTERVAL_WAIT_MILLIS);
            DomElement element = page.getElementById(idElement);
            if (element != null) {
                return element;
            }
        }
        throw new RuntimeException(String.format("Element with id '%s' not found in page url %s", idElement, page.getBaseURL()));
    }

    private WebClient getWebClient() {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        makeWebClientWaitThroughJavaScriptLoadings(webClient);
        return webClient;
    }

    private void makeWebClientWaitThroughJavaScriptLoadings(final WebClient webClient) {
        webClient.setAjaxController(new AjaxController() {
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                return true;
            }
        });
    }

    @Override
    public void close() throws IOException {
        if (webClient != null) {
            webClient.close();
        }
    }
}
