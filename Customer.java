//import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;
public class Customer {
    private JTextField nameData;
    private JTextField locationData;
    private JTable table1;
    private JButton ADDRECORDButton;
    private JButton UPDATERECORDButton;
    private JPanel customerPanel;
    private JLabel price;
    private JLabel bookName;
    private JButton BOOKSAVAILABLEButton;
    private JComboBox type;
    private JPanel dateDeli;
    private JTextField quantity;
    JFrame custF = new JFrame();
    public Customer(){
        custF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        custF.setContentPane(customerPanel);
        custF.pack();
        custF.setLocationRelativeTo(null);
        custF.setVisible(true);
        tableData();
        ADDRECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameData.getText().equals("")|| locationData.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please Fill All Fields to add Record.");
                }else{
                    try {
                        String sql = "insert into purchasedBooks"+"(NAME,ADDRESS,BOOK_NAME,PRICE)"+"values (?,?,?,?)";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/Book","root","Manish1234@");
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1,nameData.getText());
                        statement.setString(2, locationData.getText());
                        statement.setString(3, bookName.getText());
                        statement.setString(4,price.getText());
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null,"ORDER PLACED SUCCESSFULLY!");
                        nameData.setText("");
                        locationData.setText("");
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                }
            }
        });
        UPDATERECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameData.getText().equals("")) {
                        JOptionPane.showMessageDialog(null,"Please Enter Name to check");
                    }else{
                        String sql = "SELECT* from purchasedBooks WHERE NAME= '" + nameData.getText()+"'";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/Book","root","Manish1234@");
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery(sql);
                        if(!rs.next())
                            JOptionPane.showMessageDialog(null,"NO CUSTOMER FOUND");
                        else {
                            ResultSet rs1 = statement.executeQuery(sql);
                            table1.setModel(buildTableModel(rs1));
                        }
                    }
                }catch (Exception e2){
                    JOptionPane.showMessageDialog(null,"NO CUSTOMER FOUND");
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel dm = (DefaultTableModel)table1.getModel();
                int selectedRow = table1.getSelectedRow();
                bookName.setText(dm.getValueAt(selectedRow,0).toString());
                price.setText(dm.getValueAt(selectedRow,1).toString());
            }
        });
        BOOKSAVAILABLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableData();
            }
        });
    }
    public void tableData() {
        try{
            String a= "Select* from booksPresent";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/Book","root","Manish1234@");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(a);
            table1.setModel(buildTableModel(rs));
        }catch (Exception ex1){
            JOptionPane.showMessageDialog(null,ex1.getMessage());
        }
    }
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
// names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
// data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}