import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookStore {
    private JButton CUSTOMERButton;
    private JButton ADMINButton;
    private JPanel mainPanel;
    JFrame mainF = new JFrame();
    public BookStore() {
        mainF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainF.setContentPane(mainPanel);
        mainF.pack();
        mainF.setLocationRelativeTo(null);
        mainF.setVisible(true);
        CUSTOMERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Customer();
            }
        });
        ADMINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
            }
        });
    }
}