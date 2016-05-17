package br.ufs.ds3.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.io.StringReader;

import javax.swing.undo.AbstractUndoableEdit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefPreferences;
import net.sf.jabref.groups.AddToGroupAction;
import net.sf.jabref.groups.GroupSelector;
import net.sf.jabref.groups.GroupTreeNode;
import net.sf.jabref.groups.structure.AllEntriesGroup;
import net.sf.jabref.groups.structure.ExplicitGroup;
import net.sf.jabref.groups.structure.GroupHierarchyType;
import net.sf.jabref.gui.BasePanel;
import net.sf.jabref.gui.JabRefFrame;
import net.sf.jabref.importer.ParserResult;
import net.sf.jabref.importer.fileformat.BibtexParser;
import net.sf.jabref.model.database.BibDatabase;
import net.sf.jabref.model.entry.BibEntry;

public class AddToGroupActionTest {

    private static BasePanel bp;
    private static BibDatabase db;

    @BeforeClass
    public static void setUp() throws Exception {
        Globals.prefs = JabRefPreferences.getInstance();

        // Instanciar classes necessárias para o teste
        db = getBibtexDatabase();

        // Mocks
        JabRefFrame frame = Mockito.mock(JabRefFrame.class);
        bp = Mockito.mock(BasePanel.class);
        Mockito.when(bp.getDatabase()).thenReturn(db);
        Mockito.when(bp.frame()).thenReturn(frame);
        Mockito.when(bp.undoManagerAddEdit(new AbstractUndoableEdit())).thenReturn(true);
        Mockito.when(bp.getGroupSelector()).thenReturn(new GroupSelector(frame, null));
    }

    @AfterClass
    public static void teardown() {
        Globals.prefs = null;
    }

    @Test
    public void testAddEntryToExplicitGroup() {
        // Criar grupo e classes necessárias para adicionar entries
        ExplicitGroup group = new ExplicitGroup("Computação", GroupHierarchyType.getByNumber(0));
        GroupTreeNode node = new GroupTreeNode(group);
        AddToGroupAction add = new AddToGroupAction(node, false, bp);

        // Testes
        // Inserir 1 entry no grupo criado
        assertEquals(0, group.getEntries().size());

        BibEntry[] selectedEntries = new BibEntry[1];
        selectedEntries[0] = db.getEntryByKey("HipKro03");
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);
        add.actionPerformed(new ActionEvent(node, 0, "add"));

        assertEquals(1, group.getEntries().size());

        // Inserir 2 entries no grupo criado (Array tem 3 elementos, mas 1 já foi add)
        assertEquals(1, group.getEntries().size());

        selectedEntries = new BibEntry[3];
        selectedEntries[0] = db.getEntryByKey("HipKro03");
        selectedEntries[1] = db.getEntryByKey("Erhan:2010:WUP:1756006.1756025");
        selectedEntries[2] = db.getEntryByKey("erhan2010does");
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);
        add.actionPerformed(new ActionEvent(node, 0, "add"));

        assertEquals(3, group.getEntries().size());

        // Inserir 0 entry no grupo criado
        assertEquals(3, group.getEntries().size());

        selectedEntries = new BibEntry[0];
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);
        add.actionPerformed(new ActionEvent(node, 0, "add"));

        assertEquals(3, group.getEntries().size());
    }

    @Test
    public void testAddEntryToAllEntriesGroup() {
        // Criar grupo default que contém todas as referências
        AllEntriesGroup group = new AllEntriesGroup();
        GroupTreeNode node = new GroupTreeNode(group);
        AddToGroupAction add = new AddToGroupAction(node, false, bp);

        // Testes
        // Inserir 1 entry no grupo default
        BibEntry[] selectedEntries = new BibEntry[1];
        selectedEntries[0] = db.getEntryByKey("HipKro03");
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);
        add.actionPerformed(new ActionEvent(node, 0, "add"));

        assertTrue(group.contains(selectedEntries[0]));

        // Inserir 2 entries no grupo criado (Array tem 3 elementos, mas 1 já foi add)
        assertTrue(group.contains(selectedEntries[0]));

        selectedEntries = new BibEntry[3];
        selectedEntries[0] = db.getEntryByKey("HipKro03");
        selectedEntries[1] = db.getEntryByKey("Erhan:2010:WUP:1756006.1756025");
        selectedEntries[2] = db.getEntryByKey("erhan2010does");
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);
        add.actionPerformed(new ActionEvent(node, 0, "add"));

        assertTrue(group.contains(selectedEntries[0]));
        assertTrue(group.contains(selectedEntries[1]));
        assertTrue(group.contains(selectedEntries[2]));

        // Inserir 0 entry no grupo criado
        assertTrue(group.containsAll(selectedEntries));

        BibEntry[] vetorTeste = new BibEntry[0];
        Mockito.when(bp.getSelectedEntries()).thenReturn(vetorTeste);
        add.actionPerformed(new ActionEvent(node, 0, "add"));

        assertTrue(group.containsAll(selectedEntries));
    }

    @Test
    public void testAddEntryInTwoGroups() {
        ExplicitGroup group1 = new ExplicitGroup("Computação", GroupHierarchyType.getByNumber(0));
        ExplicitGroup group2 = new ExplicitGroup("Melhores artigos", GroupHierarchyType.getByNumber(0));
        GroupTreeNode node1 = new GroupTreeNode(group1);
        GroupTreeNode node2 = new GroupTreeNode(group2);
        AddToGroupAction add1 = new AddToGroupAction(node1, false, bp);
        AddToGroupAction add2 = new AddToGroupAction(node2, false, bp);

        AllEntriesGroup allEntriesGroup = new AllEntriesGroup();

        // Testes
        // Inserir 1 entry no grupo "Computação" e no grupo "Melhores artigos"
        assertEquals(0, group1.getEntries().size());
        assertEquals(0, group2.getEntries().size());

        BibEntry[] selectedEntries = new BibEntry[1];
        selectedEntries[0] = db.getEntryByKey("HipKro03");
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);

        add1.actionPerformed(new ActionEvent(node1, 0, "add"));
        assertTrue(group1.contains(db.getEntryByKey("HipKro03")));
        assertFalse(group1.contains(db.getEntryByKey("erhan2010does")));
        assertEquals(1, group1.getEntries().size());

        add2.actionPerformed(new ActionEvent(node2, 0, "add"));
        assertTrue(group2.contains(db.getEntryByKey("HipKro03")));
        assertFalse(group2.contains(db.getEntryByKey("erhan2010does")));
        assertEquals(1, group2.getEntries().size());

        // Inserir 1 entry no grupo "Computação" e não inserir no grupo "Melhores artigos"
        assertEquals(1, group1.getEntries().size());
        assertEquals(1, group2.getEntries().size());

        selectedEntries = new BibEntry[1];
        selectedEntries[0] = db.getEntryByKey("erhan2010does");
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);

        add1.actionPerformed(new ActionEvent(node1, 0, "add"));
        assertTrue(group1.contains(db.getEntryByKey("HipKro03")));
        assertTrue(group1.contains(db.getEntryByKey("erhan2010does")));
        assertEquals(2, group1.getEntries().size());

        assertTrue(group2.contains(db.getEntryByKey("HipKro03")));
        assertFalse(group2.contains(db.getEntryByKey("erhan2010does")));
        assertEquals(1, group2.getEntries().size());

        // Verificar se ambos os artigos estão no grupo AllEntries
        assertTrue(allEntriesGroup.contains(db.getEntryByKey("HipKro03")));
        assertTrue(allEntriesGroup.contains(db.getEntryByKey("erhan2010does")));
    }

    public static BibDatabase getBibtexDatabase() {
        StringReader reader = new StringReader(
                "@ARTICLE{HipKro03,\n" + "  author = {Eric von Hippel and Georg von Krogh},\n"
                        + "  title = {Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science},\n"
                        + "  journal = {Organization Science},\n" + "  year = {2003},\n" + "  volume = {14},\n"
                        + "  pages = {209--223},\n" + "  number = {2},\n"
                        + "  address = {Institute for Operations Research and the Management Sciences (INFORMS), Linthicum, Maryland, USA},\n"
                        + "  doi = {http://dx.doi.org/10.1287/orsc.14.2.209.14992}," + "\n" + "  issn = {1526-5455},"
                        + "\n" + "  publisher = {INFORMS}\n }\n" +

                        "@Article{Erhan:2010:WUP:1756006.1756025,\n"
                        + "author =     {Erhan, Dumitru and Bengio, Yoshua and Courville, Aaron and Manzagol, Pierre-Antoine and Vincent, Pascal and Bengio, Samy},"
                        + "title =      {Why Does Unsupervised Pre-training Help Deep Learning?},"
                        + "journal =    {J. Mach. Learn. Res.}," + "year =       {2010}," + "volume =     {11},"
                        + "pages =      {625--660}," + "month =      mar," + "acmid =      {1756025},"
                        + "issn =       {1532-4435}," + "issue_date = {3/1/2010}," + "numpages =   {36},"
                        + "publisher =  {JMLR.org},"
                        + "url =        {http://dl.acm.org/citation.cfm?id=1756006.1756025}" + "}\n"

                        + "@article{erhan2010does," + "title={Why does unsupervised pre-training help deep learning?},"
                        + "author={Erhan, Dumitru and Bengio, Yoshua and Courville, Aaron and Manzagol, Pierre-Antoine and Vincent, Pascal and Bengio, Samy},"
                        + "journal={The Journal of Machine Learning Research}," + "volume={11}," + "pages={625--660},"
                        + "year={2010}," + "publisher={JMLR. org}" + "}");

        BibtexParser parser = new BibtexParser(reader);
        ParserResult result = null;
        try {
            result = parser.parse();
        } catch (Exception e) {
            Assert.fail();
        }
        return result.getDatabase();
    }
}
