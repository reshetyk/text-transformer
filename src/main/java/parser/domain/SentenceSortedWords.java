package parser.domain;

import com.google.common.collect.TreeMultiset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Comparator;

import static java.util.Collections.unmodifiableCollection;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sentence")
final public class SentenceSortedWords {

    @XmlElement(name = "word")
    final private TreeMultiset<String> words = TreeMultiset.create(getCustomIgnoreCaseComparator());

    public SentenceSortedWords add(String word) {
        words.add(word);
        return this;
    }

    public int countWords() {
        return words.size();
    }

    public Collection<String> getWords() {
        return unmodifiableCollection(words);
    }


    private Comparator<String> getCustomIgnoreCaseComparator() {
        return (s1, s2) -> {
            int n1 = s1.length();
            int n2 = s2.length();
            int min = Math.min(n1, n2);
            for (int i = 0; i < min; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 != c2) {
                    c1 = Character.toUpperCase(c1);
                    c2 = Character.toUpperCase(c2);
                    if (c1 != c2) {
                        c1 = Character.toLowerCase(c1);
                        c2 = Character.toLowerCase(c2);
                        if (c1 != c2) {
                            // No overflow because of numeric promotion
                            return c1 - c2;
                        }
                    } else
                        n2 += 1;
                }
            }
            return n1 - n2;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SentenceSortedWords sentence = (SentenceSortedWords) o;

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
