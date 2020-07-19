package dev.hyein.pilot.search.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.Highlighter;

import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
public class SearchResultVo {
    private List<Document> hitDocuments;
    private Highlighter highlighter;
}
