package net.sf.jabref.logic.search;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.jabref.model.database.BibDatabase;

public class DatabaseSearcherTest {

    @Test
    public void testGetDatabasefromMatches() {
        BibDatabase database = new BibDatabase();
        SearchQuery query = new SearchQuery("asdf", true, true);

        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertTrue(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

    @Test
    public void testGetDatabasefromMatchesWithInvalidQuery() {
        BibDatabase database = new BibDatabase();
        SearchQuery query = new SearchQuery("asdf[", true, true);

        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertTrue(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }
}
