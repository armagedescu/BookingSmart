package org.filipski.view;

import org.filipski.model.Model;
import org.filipski.schema.Tester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JDialog {
    public Login(JFrame frame) {
        super(frame, true);

        setTitle("DbClick user to login");

        users = Model.getModel().getUsers();

        usersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        usersTable.addMouseListener(new MouseAdapter() {
            Login login;
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                int rw = usersTable.rowAtPoint(mouseEvent.getPoint());
                int row = table.getSelectedRow();
                if (row < 0) return;
                if (mouseEvent.getClickCount() == 2) {
                    Model.getModel().setCurrentUser(users.get(row));
                    login.setVisible(false);
                }
            }
            MouseAdapter update(Login login){this.login = login;return this;}
        }.update(this));


        Container container = this.getContentPane();
        container.add(scrollPane);

        updateData();
        this.pack();
    }
    java.util.List<Tester> users;
    JTable usersTable;
    void updateData()
    {
        String[] columnNames = {"Name", "Surname"};
        final Object[][] rowData = users.stream()
                .map (smr -> new Object[] {smr.getName(), smr.getSurname()}).toArray(Object[][]::new);
        usersTable.setModel (new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
