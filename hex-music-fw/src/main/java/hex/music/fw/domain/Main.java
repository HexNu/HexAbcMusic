package hex.music.fw.domain;

import hex.music.fw.search.TitleSearch;

/**
 * Created 2016-okt-24
 *
 * @author hl
 */
public class Main {

    public static void main(String[] args) {
        String search = "Menuett";
        SearchResultListWrapper result = new TitleSearch(search).getResult(10, 0);
        result.getResults().stream().forEach((searchResult) -> {
            System.out.println(searchResult.getText() + " " + searchResult.getPageUrl());
        });
    }
}
