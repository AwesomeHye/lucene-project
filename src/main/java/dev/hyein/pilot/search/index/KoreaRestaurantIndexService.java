package dev.hyein.pilot.search.index;

import dev.hyein.pilot.search.index.IndexService;
import dev.hyein.pilot.search.vo.KoreaRestaurantVo;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;

public class KoreaRestaurantIndexService extends IndexService {
    public KoreaRestaurantIndexService(String indexPath) {
        super(indexPath);
    }

    @Override
    public <T> void addDocument(T titlePojo, T pojo, IndexWriter indexWriter) throws IOException {
        KoreaRestaurantVo titleRow = (KoreaRestaurantVo) titlePojo;
        KoreaRestaurantVo koreaRestaurantVo = (KoreaRestaurantVo) pojo;

        Document document = new Document();

        //TextField: 토큰 후 인덱싱, StringField: 전체 인덱싱
        document.add(new TextField(titleRow.getTitle(), koreaRestaurantVo.getTitle(), Field.Store.YES));
        document.add(new StringField(titleRow.getCategory1(), koreaRestaurantVo.getCategory1(), Field.Store.YES));
        document.add(new StringField(titleRow.getCategory2(), koreaRestaurantVo.getCategory2(), Field.Store.YES));
        document.add(new StringField(titleRow.getCategory3(), koreaRestaurantVo.getCategory3(), Field.Store.YES));
        document.add(new StringField(titleRow.getRegion(), koreaRestaurantVo.getRegion(), Field.Store.YES));
        document.add(new StringField(titleRow.getCity(), koreaRestaurantVo.getCity(), Field.Store.YES));
        document.add(new TextField(titleRow.getDescription(), koreaRestaurantVo.getDescription(), Field.Store.YES));

        indexWriter.addDocument(document);
    }
}
