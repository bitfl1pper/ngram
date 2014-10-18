(ns ngram.core
  (:require [ngram.util :as util]))

;; Draft 1

;; Known issue, since it drops punctuation (cause: bad regex), false word tokens are created
;; i.e. in the canonical text you have: isn't ; this will result in tokens such as isn and t
;; 2 tokens where there should have been one. Appologies for technical debt.

;; overall this is just a sampler, optimization to come

(defn read-file-by-line
  "Read in text file."
  [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall (line-seq rdr))))

(defn tokens-word
  "Simple tokenizer, splits strings on not words.
   Accepts a string, e.g. \"This is test.\"
   Returns a vector of 'word' tokens, e.g. [\"This\" \"is\" \"test\"]"
  [s]
  (clojure.string/split s #"\W"))

(defn- drop-empty
  "Helper fn, drop empty strings from a collection of strings.
   Useful when reading large texts line by line, empty strings
   show up.

   NOTE: this could probably be fixed with better regex, but
   ill finance this operation with a bit of technical debt in
   the interest of time.

   Some of these empty strings are showing up from natural
   line breaks in the text. Some of the empty strings are showing
   up as space from after punct. is parse out. Fix later."
  [col]
  (util/drop-if "" col))

(defn word-tokens-in-col
  "Word tokens in each string in a collection of strings."
  [col]
  (flatten (map (partial tokens-word) col)))

(defn wtokens-file
  "Get the word tokens from a file.
   Read file line by line, generate seq of strings.
   Naive word tokenizer is mapped to this collection.
   The collection is flattened into one large collection of word tokens."
  [file]
  (drop-empty (word-tokens-in-col (read-file-by-line file))))

(defn unigram-frequency
  "Accepts a text file.
   Returns a collection of vectors.
   Each vector has the (word token) unigram and its frequency in the
   document.
   The collection is sorted from most frequent to least frequent."
  [file]
  (reverse (sort-by val (frequencies (wtokens-file file)))))
