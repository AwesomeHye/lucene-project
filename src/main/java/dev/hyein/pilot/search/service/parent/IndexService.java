package dev.hyein.pilot.search.service.parent;


import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public abstract class IndexService {
    private String indexPath;

    public IndexService(String indexPath){
        this.indexPath = indexPath;
    }

    public <T> Analyzer index(List<T> pojos){
        Analyzer analyzer = new KoreanAnalyzer();
        try {
            //indexWriter 인자 생성
            Directory directory = FSDirectory.open(Paths.get(indexPath));
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

            //indexWriter 생성
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

            //데이터 인덱싱 - 제목행 제외
            pojos.stream().skip(1).forEach(pojo -> {
                try {
                    addDocument(pojos.get(0), pojo, indexWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            indexWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return analyzer;
    }

    protected abstract <T> void addDocument(T titlePojo, T pojo, IndexWriter indexWriter) throws IOException;
}
