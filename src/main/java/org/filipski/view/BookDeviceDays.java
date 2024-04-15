package org.filipski.view;

import org.filipski.model.Model;
import org.filipski.schema.Smartphone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookDeviceDays extends JDialog {
    JTable registryTable = new JTable();
    JButton btnBook = new JButton("Book");
    JButton btnViewSchedules = new JButton("ViewSchedules");
    List<Smartphone> devices;

    public BookDeviceDays(JFrame frame) {
        super(frame, true);

        btnBook.setActionCommand("book");
        btnBook.addActionListener(bookListener);
        btnViewSchedules.setActionCommand("view_schedules");
        btnViewSchedules.addActionListener(bookListener);

        JScrollPane scrollPane = new JScrollPane(registryTable);
        registryTable.setFillsViewportHeight(true);

        Container container = this.getContentPane();
        Box box = Box.createHorizontalBox();
        box.add(btnBook);
        //box.add(btnClose);

        container.add(box, BorderLayout.SOUTH);
        container.add(scrollPane);

        updateData();
        this.pack();
    }

    ActionListener bookListener = new ActionListener() {
        private BookDeviceDays bookDevice;

        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "book":
                    int row = registryTable.getSelectedRow();
                    if (row < 0 || row >= devices.size()) return;
                    Smartphone device = devices.get(row);

                    DaysMax daysMax = new DaysMax(bookDevice, device.getAvailableDays());
                    daysMax.setVisible(true);
                    if(daysMax.isOk()) {
                        Model.getModel().bookNow(device, daysMax.getBook());
                    }
                    bookDevice.updateData();
                    return;
                case "view_schedules":
                    bookDevice.updateData();
                    return;
            }
        }

        private ActionListener init(BookDeviceDays var) {
            bookDevice = var;
            return this;
        }
    }.init(this);

    void updateData()
    {
        devices = Model.getModel().getDevices();
        String[] columnNames = {"ID", "Model Name", "Available", "Days Available"};
        final Object[][] rowData = devices.stream()
                .map
                        (device -> new Object[]
                        {
                                device.getId(),
                                device.getName(),
                                device.isAvailableNow() ? "Yes" : "No",
                                device.getAvailableDays()
                        }
                        ).toArray(Object[][]::new);
        registryTable.setModel(new DefaultTableModel(rowData, columnNames));
    }

}
