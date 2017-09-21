package parser.domain;

final public class Word implements Comparable<Word>{
    final private String word;

    public Word(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.toString());
    }
}
