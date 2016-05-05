package br.ufs.ds3.group;

import static org.junit.Assert.assertEquals;

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
import net.sf.jabref.groups.GroupSelector;
import net.sf.jabref.groups.GroupTreeNode;
import net.sf.jabref.groups.RemoveFromGroupAction;
import net.sf.jabref.groups.structure.ExplicitGroup;
import net.sf.jabref.groups.structure.GroupHierarchyType;
import net.sf.jabref.gui.BasePanel;
import net.sf.jabref.gui.JabRefFrame;
import net.sf.jabref.importer.ParserResult;
import net.sf.jabref.importer.fileformat.BibtexParser;
import net.sf.jabref.model.database.BibDatabase;
import net.sf.jabref.model.entry.BibEntry;


public class RemoveToGroupActionTest {

    private static BasePanel bp;
    private static BibDatabase db;


    @BeforeClass
    public static void setUp() throws Exception {
        Globals.prefs = JabRefPreferences.getInstance();

        db = getBibtexDatabase();

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
    public void removeEntryInExplicitGroup() {
        //Instancia o Primeiro grupo com 3 Entrys
        ExplicitGroup group = new ExplicitGroup("Computação", GroupHierarchyType.getByNumber(0));
        BibEntry[] entries = new BibEntry[3];
        entries[0] = db.getEntryByKey("HipKro03");
        entries[1] = db.getEntryByKey("Erhan:2010:WUP:1756006.1756025");
        entries[2] = db.getEntryByKey("erhan2010does");
        group.add(entries);
        GroupTreeNode node = new GroupTreeNode(group);

        //Testa a remoção de uma entry existente no grupo
        assertEquals(3, group.getEntries().size());
        BibEntry[] selectedEntries = new BibEntry[1];
        selectedEntries[0] = db.getEntryByKey("erhan2010does"); // seleciona a entry erhan2010does
        Mockito.when(bp.getSelectedEntries()).thenReturn(selectedEntries);

        RemoveFromGroupAction action = new RemoveFromGroupAction(node, bp);
        action.actionPerformed(new ActionEvent(node, 0, "remove"));
        assertEquals(2, group.getEntries().size());

        //Instancia o segundo grupo com uma Entry
        ExplicitGroup group2 = new ExplicitGroup("Computação2", GroupHierarchyType.getByNumber(0));
        entries = new BibEntry[1];
        entries[0] = db.getEntryByKey("HipKro03");
        group2.add(entries);
        node = new GroupTreeNode(group2);

        //A entry selecionada continua erhan2010does, e essa não existe neste grupo, portanto o tamanho não poderá reduzir
        assertEquals(1, group2.getEntries().size());
        action = new RemoveFromGroupAction(node, bp);
        action.actionPerformed(new ActionEvent(node, 0, "remove"));
        assertEquals(1, group2.getEntries().size());

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
