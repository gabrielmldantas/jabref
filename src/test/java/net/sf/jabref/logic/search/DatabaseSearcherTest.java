package net.sf.jabref.logic.search;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.jabref.model.database.BibDatabase;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;

public class DatabaseSearcherTest {

    @Test
    public void testGetDatabasefromMatchesEmptyDatabase() {
        BibDatabase database = new BibDatabase();
        SearchQuery query = new SearchQuery("asdf", true, true);

        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertTrue(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

    @Test
    public void testGetDatabasefromMatchesEmptyDatabaseWithInvalidQuery() {
        BibDatabase database = new BibDatabase();
        SearchQuery query = new SearchQuery("asdf[", true, true);

        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertTrue(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

    @Test
    public void testGetDatabasefromMatches() {
        BibDatabase database = new BibDatabase();
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.INCOLLECTION);
        entry.setField("author", "tonho");
        database.insertEntry(entry);

        SearchQuery query = new SearchQuery("tonho", true, true);
        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertFalse(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

    @Test
    public void testGetDataBaseFromMatchesEmptyEntry() {
        BibDatabase database = new BibDatabase();
        BibEntry entry = new BibEntry();
        database.insertEntry(entry);

        entry = new BibEntry();
        entry.setType(BibtexEntryTypes.INCOLLECTION);
        entry.setField("author", "tonho");
        database.insertEntry(entry);

        SearchQuery query = new SearchQuery("tonho", true, true);
        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertFalse(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

    @Test
    public void testGetDataBaseFromMatchesWithoutMatch() {
        BibDatabase database = new BibDatabase();
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.INCOLLECTION);
        entry.setField("author", "tonho");
        database.insertEntry(entry);

        SearchQuery query = new SearchQuery("asdf", true, true);
        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertTrue(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

    @Test
    public void testGetDataBaseFromMatchesWithoutMatchEmptyEntry() {
        BibDatabase database = new BibDatabase();
        BibEntry entry = new BibEntry();
        database.insertEntry(entry);

        SearchQuery query = new SearchQuery("tonho", true, true);
        DatabaseSearcher databaseSearcher = new DatabaseSearcher(query, database);

        assertTrue(databaseSearcher.getDatabasefromMatches().getEntries().isEmpty());
    }

}
