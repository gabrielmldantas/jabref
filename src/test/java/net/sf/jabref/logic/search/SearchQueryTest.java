package net.sf.jabref.logic.search;

import org.junit.Test;

import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;

import static org.junit.Assert.*;

public class SearchQueryTest {

    @Test
    public void testToString() {
        assertEquals("\"asdf\" (case sensitive, regular expression)", new SearchQuery("asdf", true, true).toString());
        assertEquals("\"asdf\" (case insensitive, plain text)", new SearchQuery("asdf", false, false).toString());
    }

    @Test
    public void testIsContainsBasedSearch() {
        assertTrue(new SearchQuery("asdf", true, false).isContainsBasedSearch());
        assertFalse(new SearchQuery("asdf", true, true).isContainsBasedSearch());
        assertFalse(new SearchQuery("author=asdf", true, false).isContainsBasedSearch());
    }

    @Test
    public void testIsGrammarBasedSearch() {
        assertFalse(new SearchQuery("asdf", true, false).isGrammarBasedSearch());
        assertFalse(new SearchQuery("asdf", true, true).isGrammarBasedSearch());
        assertTrue(new SearchQuery("author=asdf", true, false).isGrammarBasedSearch());
    }

    @Test
    public void testIsMatch() {
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.ARTICLE);
        entry.setField("author", "asdf");

        assertFalse(new SearchQuery("qwer", true, true).isMatch(entry));
        assertTrue(new SearchQuery("asdf", true, true).isMatch(entry));
        assertTrue(new SearchQuery("author=asdf", true, true).isMatch(entry));
    }

    @Test
    public void testIsValidQuery() {
        assertTrue(new SearchQuery("asdf", true, false).isValidQuery());
        assertTrue(new SearchQuery("asdf", true, true).isValidQuery());
        assertTrue(new SearchQuery("123", true, true).isValidQuery());
        assertTrue(new SearchQuery("123", true, true).isValidQuery());
        assertTrue(new SearchQuery("author=asdf", true, false).isValidQuery());
        assertTrue(new SearchQuery("author=asdf", true, true).isValidQuery());
        assertTrue(new SearchQuery("author=123", true, false).isValidQuery());
        assertTrue(new SearchQuery("author=123", true, true).isValidQuery());
    }

}