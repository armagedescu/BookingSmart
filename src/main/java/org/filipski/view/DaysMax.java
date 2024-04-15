package org.filipski.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DaysMax extends JDialog{

    private int maxBook;
    private int book = 0;
    JTextField textField = new JTextField(20);
    JButton btnOk = new JButton("Ok");
    JButton btnCancel = new JButton("Cancel");
    private boolean isOk = false;


    public DaysMax(JDialog frame, int maxBook) {
        super(frame, true);
        this.setMaxBook(maxBook);

        setTitle(String.format("Enter number of days to book, max %d", maxBook));
        textField.setText(String.format("%d", maxBook));


        btnOk.setActionCommand("ok");
        btnOk.addActionListener(daysMaxListener);
        btnCancel.setActionCommand("cancel");
        btnCancel.addActionListener(daysMaxListener);

        Container container = this.getContentPane();
        Box box = Box.createHorizontalBox();
        box.add(btnOk);
        box.add(btnCancel);

        container.add(box, BorderLayout.SOUTH);
        container.add(textField);

        updateData();
        this.pack();
    }
    ActionListener daysMaxListener = new ActionListener() {
        private DaysMax daysMax;

        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "ok":
                    try {
                        int days = Integer.parseInt(textField.getText());
                        if (days > getMaxBook()) days = getMaxBook();
                        setBook(days);
                        daysMax.setOk(true);
                        daysMax.setVisible(false);
                        return;
                    }catch (Exception exc)
                    {
                        System.out.println(exc.getMessage());
                    }
                case "cancel":
                    daysMax.setOk(false);
                    daysMax.setVisible(false);
                    return;
            }
        }

        private ActionListener init(DaysMax var) {
            daysMax = var;
            return this;
        }
    }.init(this);

    void updateData()
    {

    }

    public int getMaxBook() {
        return maxBook;
    }

    public void setMaxBook(int maxBook) {
        this.maxBook = maxBook;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
