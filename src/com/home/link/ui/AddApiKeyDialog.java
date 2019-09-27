package com.home.link.ui;

import com.home.link.itf.OnDialogActionListener;
import com.home.link.util.StringUtil;

import javax.swing.*;
import java.awt.event.*;

public class AddApiKeyDialog extends JDialog {
    private JPanel contentPane;
    private JTextField mInputText;
    private JButton mConfirmBtn;
    private JButton mCancelBtn;
    private OnDialogActionListener mListener;

    public AddApiKeyDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(mConfirmBtn);
        mConfirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        mCancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String text = mInputText.getText();
        if(StringUtil.isNotEmpty(text)){
            mListener.onConfirm(text);
            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setOnActionListener(OnDialogActionListener listener){
        this.mListener = listener;
    }


    public static void main(String[] args) {
        AddApiKeyDialog dialog = new AddApiKeyDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
