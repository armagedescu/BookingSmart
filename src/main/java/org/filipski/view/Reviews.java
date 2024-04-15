package org.filipski.view;

import org.filipski.model.Model;
import org.filipski.schema.Review;
import org.filipski.schema.Smartphone;
import org.filipski.schema.SmartphoneRegistry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Reviews extends JDialog {
    JTable registryTable = new JTable();
    List<Review> reviews;
    SmartphoneRegistry smartModel;

    public Reviews(JFrame frame, SmartphoneRegistry smartModel) {
        super(frame, true);
        this.smartModel = smartModel;



        JScrollPane scrollPane = new JScrollPane(registryTable);
        registryTable.setFillsViewportHeight(true);

        Container container = this.getContentPane();
        Box box = Box.createHorizontalBox();

        container.add(box, BorderLayout.SOUTH);
        container.add(scrollPane);

        updateData();
        this.pack();
    }

    void updateData()
    {
        reviews = Model.getModel().getReviews(smartModel);
        String[] columnNames = {"Feedback", "Rating", "Reviewed On", "Reviewed By"};
        final Object[][] rowData = reviews.stream()
                .map
                        (review -> new Object[]
                                {
                                        review.getComment(),
                                        review.getRate(),
                                        review.getDate().toString(),
                                        String.format ("%s %s", review.getReviewer().getName(), review.getReviewer().getSurname())
                                }
                        ).toArray(Object[][]::new);
        registryTable.setModel(new DefaultTableModel(rowData, columnNames));
    }

}
