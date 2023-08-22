import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DateFormat;
import java.util.Vector;
public class Admin {
    private JTextField nameData;
    private JTable table1;
    private JButton UPDATERECORDButton;
    private JPanel adminPanel;
    private JPanel date;
    JFrame adminF = new JFrame();
    JDateChooser dateChooser = new JDateChooser();
    public Admin(){
        adminF.setContentPane(adminPanel);
        adminF.pack();
        adminF.setLocationRelativeTo(null);
        adminF.setVisible(true);
        date.add(dateChooser);
        tableData();
        UPDATERECORDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String deliDate = DateFormat.getDateInstance().format(dateChooser.getDate());
                    String sql = "UPDATE purchasedBooks " +
                            "SET DELIVERY_DATE = '"+deliDate+"'"+
                            " WHERE NAME= '"+nameData.getText()+"'";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book","root","Manish1234@");
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Updated successfully");
                }catch (Exception e2){
                    System.out.println(e2);
                }
                tableData();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel dm = (DefaultTableModel)table1.getModel();
                int selectedRow = table1.getSelectedRow();
                nameData.setText(dm.getValueAt(selectedRow,0).toString());
            }
        });
    }
    public void tableData() {
        try{
            String a= "Select* from purchasedBooks";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Book","root","Manish1234@");
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