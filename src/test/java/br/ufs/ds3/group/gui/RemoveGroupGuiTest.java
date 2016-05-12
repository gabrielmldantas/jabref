package br.ufs.ds3.group.gui;

import java.io.IOException;
import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.fixture.JTreeFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.jabref.JabRefMain;
import net.sf.jabref.groups.GroupDialog;

public class RemoveGroupGuiTest extends AssertJSwingJUnitTestCase {

    private FrameFixture frameFixture;


    @BeforeClass
    public static void before() {
        FailOnThreadViolationRepaintManager.uninstall();
    }

    @Override
    protected void onSetUp() {
        ApplicationLauncher.application(JabRefMain.class).start();
        frameFixture = WindowFinder.findFrame(JFrame.class).using(robot());
    }

    @Override
    protected void onTearDown() {
        frameFixture.close();
        frameFixture = null;
    }

    @Test
    public void removeGroupTest() throws IOException {
        frameFixture.menuItemWithPath("Visualizar", "Mostrar/Esconder interface de grupos").click();
        JButtonFixture button = frameFixture.button("addgroup").click(); // Foi necessário adicionar nome ao botão
        DialogFixture popUpMenu = WindowFinder.findDialog(GroupDialog.class).using(robot()); // Group Dialog se tornou público
        JTextComponentFixture nomeGrupo = popUpMenu.textBox("name_group");
        String texto = "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123"
                + "testando123testando123testando123testando123testando123testando123";
        nomeGrupo.setText(texto);

        JButtonFixture okButton = frameFixture.button("okButton").click(); // Foi necessário adicionar nome ao botão

        JTreeFixture arvoreDeGrupos = frameFixture.tree();

        //arvoreDeGrupos.rightClickPath("Todas as referências", texto);

        System.out.println();

        //        JFileChooserFixture fileChooser = frameFixture.fileChooser();
        //        fileChooser.selectFile(new File("jabref"));
        //        frameFixture.menuItemWithPath("Arquivo", "Exportar").click();
        //        File targetFile = File.createTempFile("jabref", "test.html");
        //        fileChooser.setCurrentDirectory(targetFile.getParentFile());
        //        fileChooser.fileNameTextBox().setText(targetFile.getName());
        //        IExportFormat htmlExportFormat = ExportFormats.getExportFormat("html");
        //        fileChooser.target().setFileFilter(htmlExportFormat.getFileFilter());
        //        fileChooser.approve();
        //        frameFixture.optionPane().okButton().click();
        //        byte[] html = Files.readAllBytes(targetFile.toPath());
        //        Assert.assertTrue(html.length > 0);
    }

}
