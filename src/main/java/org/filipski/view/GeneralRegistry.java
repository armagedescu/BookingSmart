package org.filipski.view;

import org.filipski.model.Model;
import org.filipski.schema.Tester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralRegistry extends JFrame {
    JTable registryTable = new JTable();
    JButton btnLogin = new JButton("Login");
    JButton btnLogout = new JButton("Logout");
    JButton btnReviews = new JButton("Reviews");
    JButton btnReview = new JButton("Review");
    JButton btnBook = new JButton("Book");

    //Dialogs
    Login loginDialog = new Login(this);
    BookDeviceDays bookDeviceDialog = new BookDeviceDays(this);
    public GeneralRegistry() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(registryTable);
        registryTable.setFillsViewportHeight(true);

        btnLogin.setActionCommand("login");
        btnLogin.addActionListener(registryListener);
        btnLogout.setActionCommand("logout");
        btnLogout.addActionListener(registryListener);
        btnReviews.setActionCommand("reviews");
        btnReviews.addActionListener(registryListener);
        btnReview.setActionCommand("review");
        btnReview.addActionListener(registryListener);
        btnBook.setActionCommand("book");
        btnBook.addActionListener(registryListener);

        Container container = this.getContentPane();
        Box box = Box.createHorizontalBox();
        box.add(btnLogin);
        box.add(btnLogout);
        box.add(btnReviews);
        box.add(btnReview);
        box.add(btnBook);

        container.add(box, BorderLayout.SOUTH);
        container.add(scrollPane);

        updateData();
        this.pack();
    }


    ActionListener registryListener = new ActionListener() {
        private GeneralRegistry generalRegistry;

        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "login":
                    loginDialog.setVisible(true);
                    generalRegistry.updateData();
                    return;
                case "logout":
                    Model.getModel().logOut();
                    generalRegistry.updateData();
                    return;
                case "review":
                    generalRegistry.updateData();
                    return;
                case "book":
                    bookDeviceDialog.setVisible(true);
                    generalRegistry.updateData();
                    return;
            }
        }

        private ActionListener init(GeneralRegistry var) {
            generalRegistry = var;
            return this;
        }
    }.init(this);


    void updateData() {
        String[] columnNames = {"Model Name", "Devices", "Reviews", "Rating"};
        final Object[][] rowData = Model.getModel().getPhoneRegistry().stream()
                .map(smr -> new Object[]{smr.getName(), smr.getDeviceCount(), smr.getTotalReviews(), smr.getAvgRating()}).toArray(Object[][]::new);
        registryTable.setModel(new DefaultTableModel(rowData, columnNames));
        Tester tester = Model.getModel().getCurrentUser();
        if (Model.getModel().isLoggedIn()) {
            this.setTitle(String.format("Logged in as %s %s", tester.getName(), tester.getSurname()));
            btnLogin.setVisible(false);
            btnLogout.setVisible(true);
            btnReviews.setVisible(true);
            btnReview.setVisible(true);
            btnBook.setVisible(true);
        } else {
            this.setTitle("Phone Registry: Not logged in");
            btnLogin.setVisible(true);
            btnLogout.setVisible(false);
            btnReviews.setVisible(true);
            btnReview.setVisible(false);
            btnBook.setVisible(false);
        }
    }
}