package dev.hyein.pilot.search;

import dev.hyein.pilot.search.config.CommonConfig;
import dev.hyein.pilot.search.helper.CsvLoader;
import dev.hyein.pilot.search.index.KoreaRestaurantIndexService;
import dev.hyein.pilot.search.search.KoreaRestaurantSearchService;
import dev.hyein.pilot.search.search.SearchService;
import dev.hyein.pilot.search.index.IndexService;
import dev.hyein.pilot.search.vo.KoreaRestaurantVo;
import dev.hyein.pilot.search.search.SearchResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.Highlighter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class SearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

    @Autowired
    private CommonConfig commonConfig;

    @Override
    public void run(String... args) throws Exception {
        log.info(commonConfig.getCsvPath());

        //파일 vo에 매핑하기
        List<KoreaRestaurantVo> koreaRestaurantVos = CsvLoader.readCsv(new KoreaRestaurantVo(), commonConfig.getCsvPath());
        log.info("data size: {}", koreaRestaurantVos.size());

        //인덱싱
        IndexService indexService = new KoreaRestaurantIndexService(commonConfig.getIndexPath());
        Analyzer analyzer = indexService.index(koreaRestaurantVos);

        //서칭
        SearchService searchService = new KoreaRestaurantSearchService(commonConfig.getIndexPath(), koreaRestaurantVos.get(0));
        SearchResultVo searchResultVo = searchService.search(Integer.MAX_VALUE);



        //서칭 결과 출력
        List<Document> hitDocuments = searchResultVo.getHitDocuments();
        Highlighter highlighter = searchResultVo.getHighlighter();
        for(int i = 0; i < hitDocuments.size(); i++){
//            log.info("{}. document: {}", i+1, hitDocuments.get(i));
            Document doc = hitDocuments.get(i);
            log.info("{}. doc: {}", i+1, doc);
            String text = doc.get(koreaRestaurantVos.get(0).getDescription());
            log.info("{}. text: {}", i+1, text);
            TokenStream tokenStream = analyzer.tokenStream(koreaRestaurantVos.get(0).getDescription(), text);
            String[] frags = highlighter.getBestFragments(tokenStream, text, 10);
            log.info("{}. document: {}", i+1, Arrays.stream(frags).collect(Collectors.joining(", ")));

        }
    }

}
