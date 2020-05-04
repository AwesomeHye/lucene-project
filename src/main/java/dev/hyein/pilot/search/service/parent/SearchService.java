package dev.hyein.pilot.search.service.parent;

import dev.hyein.pilot.search.vo.SearchResultVo;
import lombok.Data;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public abstract class SearchService<T> {
    private T titleRow;
    private  String indexPath;
    public SearchService(String indexPath, T pojo){
        this.indexPath = indexPath;
        titleRow = pojo;
    }

    public SearchResultVo search(int hitCount){
        List<Document> hitDocuments = new ArrayList<>();
        Highlighter highlighter = null;
        try {
            Query query = getQuery();
            //검색
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            TopDocs docs = indexSearcher.search(query, hitCount);
            ScoreDoc[] hits = docs.scoreDocs;

            //검색된 문서 가져오기
            hitDocuments = Arrays.stream(hits).map(hit -> {
                Document document = null;
                try {
                    document = indexSearcher.doc(hit.doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return document;
            }).collect(Collectors.toList());

            indexReader.close();

            //하이라이팅
            highlighter = getHighlighter(query);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SearchResultVo(hitDocuments, highlighter);
    }

    private Highlighter getHighlighter(Query query) {
        QueryScorer scorer =  new QueryScorer(query);
        Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(), scorer);
        highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 10));
        return highlighter;
    }

    protected abstract Query getQuery();



}

