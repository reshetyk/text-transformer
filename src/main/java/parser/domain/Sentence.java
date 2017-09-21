package parser.domain;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

final public class Sentence {
    final private Multiset<Word> words = TreeMultiset.create();

    public Sentence add(Word word) {
        words.add(word);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        if (words != null ? !words.equals(sentence.words) : sentence.words != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return words != null ? words.hashCode() : 0;
    }

    @Override
    public String toString() {
        return words.toString();
    }
}
