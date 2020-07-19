package dev.hyein.pilot.search.search;

import dev.hyein.pilot.search.search.SearchService;
import dev.hyein.pilot.search.vo.KoreaRestaurantVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

@Slf4j
public class KoreaRestaurantSearchService extends SearchService {
    public KoreaRestaurantSearchService(String indexPath, KoreaRestaurantVo titleRow) {
        super(indexPath, titleRow);
    }

    @Override
    protected Query getQuery() {

//        return twoOrQueries("바다", "식당");
        Query query = wildcardQuery("바다*");
        log.info("query: {}", query);
        return query;

    }

    private Query twoOrQueries(String k1, String k2){
        TermQuery k1Query = new TermQuery(new Term(((KoreaRestaurantVo) getTitleRow()).getDescription(), k1));
        TermQuery k2Query = new TermQuery(new Term(((KoreaRestaurantVo) getTitleRow()).getDescription(), k2));

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(k1Query, BooleanClause.Occur.SHOULD)
                .add(k2Query, BooleanClause.Occur.SHOULD)
                .build();
        return booleanQuery;
    }

    private Query titleAndDescriptionField(String searchKeyword){
        TermQuery titleQuery = new TermQuery(new Term(((KoreaRestaurantVo) getTitleRow()).getTitle(), searchKeyword));
        TermQuery desQuery = new TermQuery(new Term(((KoreaRestaurantVo) getTitleRow()).getDescription(), searchKeyword));

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(titleQuery, BooleanClause.Occur.MUST)
                .add(desQuery, BooleanClause.Occur.MUST)
                .build();
        return booleanQuery;
    }

    private Query wildcardQuery(String keyword){
        WildcardQuery wildcardQuery = new WildcardQuery(new Term(((KoreaRestaurantVo) getTitleRow()).getDescription(), keyword));
        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(wildcardQuery, BooleanClause.Occur.MUST)
                .build();
        return booleanQuery;
    }

}
