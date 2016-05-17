package br.ufs.ds3.group;

import static org.junit.Assert.*;
import net.sf.jabref.groups.GroupTreeNode;
import net.sf.jabref.groups.structure.ExplicitGroup;
import org.mockito.Mockito;

import org.junit.Test;

public class AddAndRemoveGroupTest {

    /*testar o método de adicionar um grupo a outro grupo,
    o método add é abstraido em GroupTreeNode desde a classe DefaultMutableTreeNode */

    /*partição por classes de equivalência, a entrada possui a condição de que o
    grupo a ser adicionado não pode ser ancestor do grupo de destino.


    Quando o método não aceita a classe correta ele lança uma exceção
     */

    @Test(expected = IllegalArgumentException.class)
    public void testAdd() {

        ExplicitGroup group1 = Mockito.mock(ExplicitGroup.class);
        GroupTreeNode node = new GroupTreeNode(group1);

        //node do grupo 1 inicia com 0 filhos
        assertEquals(0, node.getChildCount());

        //inserindo dois grupos distintos que atendem aos requisitos da adição
        ExplicitGroup group2 = Mockito.mock(ExplicitGroup.class);
        GroupTreeNode node2 = new GroupTreeNode(group2);

        ExplicitGroup group3 = Mockito.mock(ExplicitGroup.class);
        GroupTreeNode node3 = new GroupTreeNode(group3);

        node.add(node2);
        node.add(node3);

        assertEquals(2, node.getChildCount());

        //tentando adicionar um grupo como subgrupo dele mesmo
        node.add(node);

    }

    /*testar o método de remoção de um grupo,
    o método remove é abstraido em GroupTreeNode desde a classe DefaultMutableTreeNode */

    /*partição por classes de equivalência, a entrada possui a condição de que o
    grupo a ser removido deve ser filho do grupo do nó que chama o método.


    Quando o método não aceita a classe correta ele lança uma exceção
     */

    @Test(expected = IllegalArgumentException.class)
    public void testRemove() {

        ExplicitGroup group1 = Mockito.mock(ExplicitGroup.class);
        GroupTreeNode node = new GroupTreeNode(group1);

        //inicia com 0 filhos logo após a criação do grupo/nó
        assertEquals(0, node.getChildCount());

        ExplicitGroup group2 = Mockito.mock(ExplicitGroup.class);
        GroupTreeNode node2 = new GroupTreeNode(group2);

        //adiciona o grupo 2 ao grupo 1
        node.add(node2);

        assertEquals(1, node.getChildCount());

        //remove o grupo 2 que está dentro do grupo 1

        node.remove(node2);

        assertEquals(0, node.getChildCount());

        //tentando remover o grupo 2 novamente, sendo que ele já foi removido

        node.remove(node2);

    }

}
