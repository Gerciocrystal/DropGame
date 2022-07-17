import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.lang.*;
	class App extends JFrame implements ActionListener
	{
		TextField txtnome,txtid,txtidade;
		JButton btn;
		public App(){
			setLayout(new FlowLayout());
			setSize(400,600);
			txtid=new TextField("Seu id");
			txtnome=new TextField("Seu nome");
			txtidade=new TextField("Sua idade");
			btn=new JButton("Save");
			add(txtid); add(txtnome); add(txtidade);
			add(btn);
			btn.addActionListener(this);
		}
		public void actionPerformed(ActionEvent e){
			try{
				Connection cn = null;
				Statement stm = null;
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				cn = DriverManager.getConnection("jdbc:mysql://localhost/pessoa?user=root&password=");
				stm = cn.createStatement();
				stm.executeUpdate("insert into estudante values('"+ txtid.getText()+"','"+ txtnome.getText() +"','"+ txtidade.getText() +"')");
				JOptionPane.showMessageDialog(this,"Dados Gravados");
			}catch(Exception ex)
			{ 
				JOptionPane.showMessageDialog(this,"Houve Erro:"+ex);
			}
		}
public static void main(String a[])
	{
		try{
				Connection cn = null;
				Statement stm = null;
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				cn = DriverManager.getConnection("jdbc:mysql://localhost/pessoa?user=root&password=");
				stm = cn.createStatement();
			//	stm.executeUpdate("insert into estudante values('"+ txtid.getText()+"','"+ txtnome.getText() +"','"+ txtidade.getText() +"')");
				JOptionPane.showMessageDialog(null,"Dados Gravados");
			}catch(Exception ex)
			{ 
				JOptionPane.showMessageDialog(null,"Houve Erro:"+ex);
			}
		new App().setVisible(true);
	}
}