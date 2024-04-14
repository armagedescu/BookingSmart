package org.filipski.view;

import org.filipski.model.Model;
import org.filipski.schema.SmartphoneRegistry;
import org.filipski.schema.Tester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralRegistry extends JFrame {
    JButton btnLogin = new JButton( "Login" );
    JButton btnReviews = new JButton( "Reviews" );
    JButton btnReview = new JButton( "Review" );
    public GeneralRegistry() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registryTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(registryTable);
        registryTable.setFillsViewportHeight(true);

        btnLogin.setActionCommand("login");
        btnLogin.addActionListener(automobilListener);
        btnReviews.setActionCommand("reviews");
        btnReviews.addActionListener(automobilListener);
        btnReview.setActionCommand("review");
        btnReview.addActionListener(automobilListener);

        Container container = this.getContentPane();
        Box box = Box.createHorizontalBox();
        box.add(btnLogin);
        box.add(btnReviews);

        container.add( box, BorderLayout.SOUTH );
        container.add(scrollPane);


        updateData();
        this.pack();
    }
    Login loginDialog = new Login(this, true);
    ActionListener automobilListener = new ActionListener() {
        private GeneralRegistry generalRegistry;

        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "login":
                    loginDialog.setVisible(true);
                    generalRegistry.updateData();
                    return;
                case "review":
                    generalRegistry.updateData();
                    return;
                case "book":
                    generalRegistry.updateData();
                    return;
            }
        }
        private ActionListener init(GeneralRegistry var){
            generalRegistry = var;
            return this;
        }
    }.init(this);
    JTable registryTable;

    void updateData()
    {
        String[] columnNames = {"Model Name"};
        final Object[][] rowData = Model.getModel().getRegistry().stream()
                        .map (smr -> new Object[] {smr.getName()}).toArray(Object[][]::new);
        registryTable.setModel (new DefaultTableModel(rowData, columnNames) );
        Tester tester = Model.getModel().getCurrentUser();
        if(tester !=  null) {
            btnLogin.setVisible(false);
            this.setTitle(String.format("Logged in as %s %s", tester.getName(), tester.getSurname()));
        }
    }
}
