package com.api.textsearch.lucene.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.api.textsearch.exceptionhandler.UserFriendlyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import static com.api.textsearch.support.ErrorMessage.USER_FRIENDLY_MESSAGE;

@Slf4j
public class InMemoryLuceneIndex
{
    private Directory memoryIndex;
    private Analyzer analyzer;

    public InMemoryLuceneIndex(Directory memoryIndex, Analyzer analyzer)
    {
        super();
        this.memoryIndex = memoryIndex;
        this.analyzer = analyzer;
    }

    /**
     *
     * @param field
     * @param body
     */
    public void indexDocument(String field, String body)
    {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try
        {
            IndexWriter indexWriter = new IndexWriter(memoryIndex, indexWriterConfig);
            Document document = new Document();

            document.add(new TextField(field, body, Field.Store.YES));

            indexWriter.addDocument(document);
            indexWriter.close();
        } catch (IOException e) {
            log.error("Error while creating document : {}", e.getMessage());
            throw new UserFriendlyException(USER_FRIENDLY_MESSAGE);
        }
    }

    /**
     *  Deletes index
     * @throws IOException
     */
    public void deleteDocument()
    {
        try {
            memoryIndex.close();
        } catch (IOException e) {
            log.error("Error while deleting memory index : {}", e.getMessage());
            throw new UserFriendlyException(USER_FRIENDLY_MESSAGE);
        }
    }

    /**
     * Counts number of terms in the text
     * @param query
     * @param field
     * @return
     * @throws IOException
     */
    public long countTerms(String query, String field)
    {
        try {
            IndexReader indexReader = DirectoryReader.open(memoryIndex);
            return indexReader.totalTermFreq(new Term(field, query));
        } catch (IOException e) {
            log.error("Error while counting term frequency : {}", e.getMessage());
            throw new UserFriendlyException(USER_FRIENDLY_MESSAGE);
        }
    }

    /**
     * Find similar words based on minimum number of edits
     * @param query
     * @param field
     * @param minimumEdits
     * @return
     */

    public List<String> similarWords(String query, String field, int minimumEdits)
    {
        try {
            List<String> values = new ArrayList<>();
            IndexReader indexReader = DirectoryReader.open(memoryIndex);

            Terms terms = MultiTerms.getTerms(indexReader, field);
            if (terms == null) new ArrayList<>();

            Term term = new Term(field, query);
            FuzzyTermsEnum fuzzyTermsIterator = new FuzzyTermsEnum(terms, term, minimumEdits, 0, false);

            BytesRef val;
            BytesRef searched = term.bytes();
            while ((val = fuzzyTermsIterator.next()) != null)
            {
                if (!searched.bytesEquals(val))
                    values.add(val.utf8ToString());
            }
            return values;
        } catch (IOException e) {
            log.error("Error while calculating similar words : {}", e.getMessage());
            throw new UserFriendlyException(USER_FRIENDLY_MESSAGE);
        }

    }
}
