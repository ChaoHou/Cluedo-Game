package cluedo.view;

import cluedo.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class BoardFrame extends JFrame {
    private final BoardCanvas canvas;
    private boolean rollDisabled;
    private boolean refuteDisabled;

    private JButton[] buttons = new JButton[2];

    public BoardFrame(String title, Board game, MouseListener mouse, ActionListener action) {
        super(title);

        canvas = new BoardCanvas(/* uid,*/game);
        canvas.addMouseListener(mouse);

        //does this line necessary?
        setLayout(new BorderLayout());

        add(canvas, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create JButtons and add ActionListener received
        JButton ac = new JButton("ACCUSATION");
        buttons[0] = ac;
        JButton as = new JButton("ASSUMPTION");
        buttons[1] = as;
//        ac.addActionListener(action);
//        as.addActionListener(action);

        //create combo boxes
        Card.CHARACTER[] charaT = Card.CHARACTER.values();
        Card.WEAPON[] weaponT = Card.WEAPON.values();
        Card.ROOM[] roomT = Card.ROOM.values();
        String[] charas = new String[charaT.length];
        String[] weapons = new String[weaponT.length];
        String[] rooms = new String[roomT.length];
        for (int i = 0; i < charaT.length; ++i) {
            charas[i] = charaT[i].toString();
            weapons[i] = weaponT[i].toString();
        }
        for (int i = 0; i < roomT.length; ++i) {
            rooms[i] = roomT[i].toString();
        }


        JComboBox chara = new JComboBox(charas);
        JComboBox weapon = new JComboBox(weapons);
        JComboBox room = new JComboBox(rooms);

        //create panel for buttons and combo box
        JPanel p = new JPanel();
        p.add(chara);
        p.add(weapon);
        p.add(room);
        p.add(ac);
        p.add(as);

        //message console
//        JTextArea textArea = new JTextArea();
//        p.add(textArea,BorderLayout.SOUTH);

//        add(p,BorderLayout.SOUTH);

        //pack components nicely
        pack();
        //we don't want user to change window size
        setResizable(false);

        //set UI visible
        setVisible(true);
    }

    /**
     * returns string where user clicked
     * REFUTE returned with a number which is index of card (user may have from 3 to 6 cards)
     *
     *
     * @return String
     * UP, DOWN, LEFT, RIGHT
     * REFUTE[0-5]
     * ROLL
     * NULL
     */
    public String defineClick() {
        return canvas.defineClick();
    }

    /**
     *  disable the roll button
     */
    public void disableRoll() {
        rollDisabled = true;
    }

    public void enableRoll() {
        rollDisabled = false;
    }

    /**
     * disabled the ability to make announcement or accusation. For example disable buttons.
     */
    public void disableAction() {
        for (JButton b: buttons) {
            b.setEnabled(false);
        }
    }

    public void enableAction() {
        for (JButton b: buttons) {
            b.setEnabled(true);
        }
    }

    /**
     * disable the ability to select a card to refute.
     *
     */
    public void disableRefute() {
        refuteDisabled = true;
    }

    public void enableRefute() {
        refuteDisabled = false;
    }

    /**
     * show popups to ask user to enter a name and select a type of token.
     * @return 0:username 1:selected character in Uppercase
     */

    public String[] initPlayer() {
        String[] input = new String[2];
        Card.CHARACTER[] temps = Card.CHARACTER.values();
        String[] charas = new String[temps.length];
        for (int i = 0; i < temps.length; i++) {
            charas[i] = temps[i].toString();

        }

        input[0] = JOptionPane.showInputDialog(this, "What's your name?");
        input[1] =(String) JOptionPane.showInputDialog(this, "Select a character.", "Character select", JOptionPane.INFORMATION_MESSAGE, null, charas, charas[0]);

        return input;
    }

    /**
     *  show messages to user
     * @param str
     */
    public void showMessage(String str) {
        JOptionPane.showMessageDialog(this, str, "Listen!", JOptionPane.PLAIN_MESSAGE);
    }

    public void repaint() {
        canvas.repaint();
    }


}
