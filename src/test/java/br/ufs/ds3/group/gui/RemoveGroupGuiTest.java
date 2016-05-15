package br.ufs.ds3.group.gui;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.fixture.JPopupMenuFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.fixture.JTreeFixture;
import org.assertj.swing.image.ScreenshotTaker;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.util.Files.currentFolder;
import static java.io.File.separator;
import java.io.File;
import java.io.IOException;

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
    public void removeGroupTest() {
        // Ativar aba lateral onde se localizam os grupos
        frameFixture.menuItemWithPath("Visualizar", "Mostrar/Esconder interface de grupos").click();

        // Clica no botão para adicionar grupo
        frameFixture.button("addgroup").click(); // Foi necessário adicionar nome ao botão

        // Preenche nome do grupo e clica em OK
        DialogFixture popUpMenu = WindowFinder.findDialog(GroupDialog.class).using(robot()); // Group Dialog se tornou público
        JTextComponentFixture nomeGrupo = popUpMenu.textBox("name_group");
        String texto = "teste";
        nomeGrupo.setText(texto);
        frameFixture.button("okButton").click(); // Foi necessário adicionar nome ao botão

        // Abre o popup de opções sobre o grupo criado
        JTreeFixture arvoreDeGrupos = frameFixture.tree();
        arvoreDeGrupos.rightClickRow(1);

        // Seleciona a opção de remover grupos e subgrupos
        JPopupMenuFixture popUpMenu2 = arvoreDeGrupos.showPopupMenuAt(1);
        popUpMenu2.menuItemWithPath("Remover grupos e subgrupos").click();

        // Confirma a remoção
        JOptionPaneFixture confirmationDialog = frameFixture.optionPane();
        confirmationDialog.yesButton().click();
    }

    @Test
    public void removeGroupWithLongNameTest() {
        // Ativar aba lateral onde se localizam os grupos
        frameFixture.menuItemWithPath("Visualizar", "Mostrar/Esconder interface de grupos").click();

        // Clica no botão para adicionar grupo
        frameFixture.button("addgroup").click(); // Foi necessário adicionar nome ao botão

        // Abre o popup de opções sobre o grupo criado
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

        // Seleciona a opção de remover grupos e subgrupos
        frameFixture.button("okButton").click(); // Foi necessário adicionar nome ao botão

        // Seleciona a opção de remover grupos e subgrupos
        JTreeFixture arvoreDeGrupos = frameFixture.tree();
        arvoreDeGrupos.rightClickRow(1);

        // Seleciona a opção de remover grupos e subgrupos
        JPopupMenuFixture popUpMenu2 = arvoreDeGrupos.showPopupMenuAt(1);
        popUpMenu2.menuItemWithPath("Remover grupos e subgrupos").click();

        // Confirma a remoção
        JOptionPaneFixture confirmationDialog = frameFixture.optionPane();
        printScreen(); // tira um print screen do erro
        confirmationDialog.yesButton().click();
    }

    private void printScreen() {
        ScreenshotTaker screenshotTaker = new ScreenshotTaker();
        String currentFolderPath = null;
        try {
            currentFolderPath = currentFolder().getCanonicalPath();
            File imageFolder = new File(currentFolderPath + separator + "failed-tests");
            String imageFolderPath = imageFolder.getCanonicalPath() + separator;
            screenshotTaker.saveDesktopAsPng(imageFolderPath + "myTest.png");
        } catch (IOException e1) {
        }

    }
}