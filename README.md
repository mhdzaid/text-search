## Text Search
### How To Run
* Project is running on port `8080`
* The API call is endpoint is
```
    http://localhost:8080/api/v1/words?similar=
```
* The word whose frequency and similar words are to be determined is the `Request Parameter` `similar=` e.g `similar=Word`
* The `Request Body` should be
```
    {
        "text":"Word Word Word word"
    }
```
* The `Response` should be 
```
    {
        "similarWords": [
            "word"
        ],
        "wordFrequency": 3
    }
```
### Scope of Project
* I've implemented text search using `Apache Lucene` which has a lot of features.
* The time taken for me to develop this API was 3 hours which mostly involved reading `Apache Lucene` docs and testing different implementations.
* It is intentionally case-senstive e.g `word` and `Word` are currently considered as `similar` but not equal.
* The reason for choosing `Apache Lucene` is that even if we want to make changes later on e.g to add `case sensitivity`, it would be easy to do so by just changing the tokenizer.

### How to bring solution further
* I took the liberty to make an extra change of disregarding commas while detecting words.
* Currently the API can detect words regardless of commas, e.g in text `word, Word` frequency of `word` would be `1`; 
* Given more time I would have liked to test it using further punctuations like `. ? : " " ` etc.
