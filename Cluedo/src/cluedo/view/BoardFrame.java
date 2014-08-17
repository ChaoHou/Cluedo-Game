package cluedo.view;

import cluedo.controller.action.server.Move;
import cluedo.model.*;
import com.sun.codemodel.internal.JOp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class BoardFrame extends JFrame {
    private final BoardCanvas canvas;
    private boolean rollDisabled;
    private boolean refuteDisabled;

    private JButton[] buttons = new JButton[2];
    private JComboBox[] combos = new JComboBox[3];

    public BoardFrame(String title, Board game, MouseListener mouse, ActionListener action) {
        super(title);

        canvas = new BoardCanvas(game,1/*mouse.getUid()*/);
        canvas.addMouseListener(mouse);

        //does this line necessary?
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create JButtons and add ActionListener received
        JButton ac = new JButton("ACCUSATION");
        buttons[0] = ac;
        JButton as = new JButton("ASSUMPTION");
        buttons[1] = as;
        ac.addActionListener(action);
        as.addActionListener(action);

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
        combos[0] = chara;
        JComboBox weapon = new JComboBox(weapons);
        combos[1] = weapon;
        JComboBox room = new JComboBox(rooms);
        combos[2] = room;

        //create panel for buttons and combo box
        JPanel p = new JPanel();
        p.add(chara);
        p.add(weapon);
        p.add(room);
        p.add(ac);
        p.add(as);

        //message console
        JPanel q = new JPanel();
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        q.add(textArea, BorderLayout.SOUTH);

        //create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem exit = new JMenuItem(new CloseAction(this));

        menu.add(exit);

        menuBar.add(menu);

        add(canvas, BorderLayout.NORTH);
        add(p,BorderLayout.CENTER);
        add(textArea,BorderLayout.SOUTH);
        setJMenuBar(menuBar);

        //pack components nicely
        pack();
        //we don't want user to change window size
        setResizable(false);

        //set UI visible
        setVisible(true);
    }

    /**
     *  disable the roll button
     */
    public void disableRoll() {
        rollDisabled = true;
    }

    public void enableRoll() {
        rollDisabled = false;
        disableAction();
        disableRefute();
    }

    /**
     * disabled the ability to make announcement or accusation. For example disable buttons.
     */
    public void disableAction() {
        for (JButton b: buttons) {
            b.setEnabled(false);
        }
        for (JComboBox c: combos) {
            c.setEnabled(false);
        }
    }

    public void enableAction() {
        for (JButton b: buttons) {
            b.setEnabled(true);
        }
        for (JComboBox c: combos) {
            c.setEnabled(true);
        }
        disableRoll();
        disableRefute();
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
        disableRoll();
        disableAction();
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

        while(input[0] == null || input[0].equals("")) {
            input[0] = JOptionPane.showInputDialog(this, "What's your name?");
        }
        while(input[1] == null) {
        input[1] =(String) JOptionPane.showInputDialog(this, "Select a character.", "Character select", JOptionPane.INFORMATION_MESSAGE, null, charas, charas[0]);
        }

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


    private class CloseAction extends AbstractAction {
        private JFrame frame;
        public CloseAction(JFrame frame) {
            super("Exit");
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int responce = JOptionPane.showConfirmDialog(frame,"Are you sure you want to close the app?\nThe progress will be discarded.");
            if (responce == JOptionPane.OK_OPTION) {
                frame.setVisible(false);
            }
        }
    }

    /**
     * check whether the (x,y) is on dice and return a boolean
     * @param x
     * @param y
     * @return
     */
    public boolean clickOnDie(int x, int y) {
        if (rollDisabled){return false;}
        String[] temp = canvas.defineClick(x,y);
        if (temp[0] != null && temp[0].equals("Dice")) {
            return true;}
        return false;
    }

    /**
     * check whether the (x,y) is on a card
     * @param x
     * @param y
     * @return a Card clicked or return null if not on any cards
     */
    public Card clickOnHand(int x, int y) {
        if (refuteDisabled) {return null;}
        String[] temp = canvas.defineClick(x,y);
        if (temp[0] != null && temp[0].equals("Card")) {
            return new Card(Card.TYPE.values()[Integer.parseInt(temp[1])], temp[2]);}
        return null;
    }

    /**
     * check whether clicking on an arrow
     * @param x
     * @param y
     * @return the direction, if not on an arrow, return null
     */
    public Move.Direction clickOnArrow(int x, int y) {
        String[] temp = canvas.defineClick(x,y);
        if (temp[0] != null && temp[0].equals("Move")) {
            return Move.Direction.values()[Integer.parseInt(temp[1])];}
        return null;
    }

    public boolean clickOnPass(int x, int y) {
        if (refuteDisabled) {return false;}
        String[] temp = canvas.defineClick(x,y);
        if(temp[0] != null && temp[0].equals("Pass")) {
            return true;
        }
        return false;
    }
}
