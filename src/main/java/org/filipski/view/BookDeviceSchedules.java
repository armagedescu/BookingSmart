package org.filipski.view;

import org.filipski.model.Model;
import org.filipski.schema.Schedule;
import org.filipski.schema.Smartphone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookDeviceSchedules extends JDialog {
    JTable scheduleTable = new JTable();
    JButton btnBookCurrent = new JButton("Book this");
    JButton btnBookOnEnd = new JButton("Book on end");


    List<Schedule> schedules;
    Smartphone device;


    public BookDeviceSchedules(JDialog frame, Smartphone device) {
        super(frame, true);
        this.device = device;


        btnBookCurrent.setActionCommand("book_current");
        btnBookCurrent.addActionListener(bookListener);
        btnBookOnEnd.setActionCommand("book_on_end");
        btnBookOnEnd.addActionListener(bookListener);

        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scheduleTable.setFillsViewportHeight(true);

        Container container = this.getContentPane();
        Box box = Box.createHorizontalBox();

        box.add(btnBookCurrent);
        box.add(btnBookOnEnd);


        container.add(box, BorderLayout.SOUTH);
        container.add(scrollPane);

        updateData();
        this.pack();
    }


    ActionListener bookListener = new ActionListener() {
        private BookDeviceSchedules bookDeviceSchedules;

        public void actionPerformed(ActionEvent e) {


            switch (e.getActionCommand()) {

                case "book_current":
                    {
                        Schedule schedule = getCurrentSchedule();
                        if (schedule == null) return;
                        if (!schedule.isUnallocated()) return;

                        DaysMax daysMax = new DaysMax(bookDeviceSchedules, schedule.getMaxDays());
                        daysMax.setVisible(true);
                        if (daysMax.isOk()) {
                            Model.getModel().bookNow(schedule);
                        }
                    }
                bookDeviceSchedules.updateData();
                    return;
                case "book_on_end":
                    if (device == null) return;
                    {
                        Schedule schLast = schedules.getLast();
                        DaysMax daysMax = new DaysMax(bookDeviceSchedules, 100);
                        daysMax.setVisible(true);
                        if (daysMax.isOk()) {
                            //Smartphone smartphone, Tester reviewer, LocalDateTime start, int days)
                            int book = daysMax.getBook();
                            Schedule schedule = new Schedule(device, Model.getModel().getCurrentUser(), schLast.getFinish().plusDays(1), book);
                            Model.getModel().bookNow(schedule);
                        }
                        bookDeviceSchedules.updateData();
                    }
                    return;
            }
        }

        private ActionListener init(BookDeviceSchedules var) {
            bookDeviceSchedules = var;
            return this;
        }
    }.init(this);
    Schedule getCurrentSchedule()
    {
        int row = scheduleTable.getSelectedRow();
        if (row < 0 || row >= schedules.size()) return null;
        return schedules.get(row);
    }


    void updateData()
    {
        schedules = Model.getModel().getSchedules(device);
        String[] columnNames = {"Start", "Finish", "Free To Book"};
        //*
        final Object[][] rowData = schedules.stream()
                .map
                        (schedule -> new Object[]
                                {
                                        schedule.getStart(),
                                        schedule.getFinish(),
                                        schedule.isUnallocated()
                                }
                        ).toArray(Object[][]::new);//*/
        scheduleTable.setModel(new DefaultTableModel(rowData, columnNames));
    }

}
