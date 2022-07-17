  import javax.swing.*;
  import javax.swing.table.DefaultTableModel;
  import javax.swing.table.TableModel;
  import java.awt.*;
  import java.awt.event.*;
  import java.util.Random;

  import java.sql.*;

 class Coneccao{
    Connection con=null;
    public Connection connectarBD() throws Exception{


        try{
            // conector:mysq; :// onde ela esta: numero da porta(option)/nome_da_Bd?user=nome&password=
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://localhost/pessoa?user=root&password=");// passando url pronta

        }
        catch(SQLException e){

            JOptionPane.showMessageDialog(null,e.getMessage());
        }


        return con;
    }
    public  void visualizarTable() throws SQLException{


        Statement statement= con.createStatement();

        ResultSet resultSet= statement.executeQuery("SELECT * FROM usuario");

        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        for ( int i = 1; i <= numberOfColumns; i++ )
            System.out.printf( "%-8s\t", metaData.getColumnName( i ) );
        System.out.println();

        while (resultSet.next() )
             {
             for ( int i = 1; i <= numberOfColumns; i++ )
                 System.out.printf( "%-8s\t", resultSet.getObject( i ) );
             System.out.println();
             }


    }
    public  void adiconarTabela () throws SQLException {

        Statement statement= con.createStatement();

        ResultSet resultSet= statement.executeQuery("INSERT INTO usuario(nome_usuario,senha_usuario)" +
                "VALUES('jonas','01')");
    }
    public  void delectarTabela () throws SQLException{
        Statement statement= con.createStatement();

        ResultSet resultSet= statement.executeQuery("DELETE FROM usuario WHERE senha_usuario='01'");

    }
    public void addicionarTabela(String nome,int point) throws SQLException{
        Statement statement= con.createStatement();

         statement.executeUpdate("INSERT INTO pontuacao(name,point) VALUES('"+ nome+"','"+ point +"')");
    }
    public void selecionarTabela() throws SQLException{
        Statement statement= con.createStatement();

        ResultSet resultSet =statement.executeQuery("SELECT * FROM pontuacao ORDER BY point DESC");


        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        for ( int i = 1; i <= numberOfColumns; i++ )
            System.out.printf( "%-8s\t", metaData.getColumnName( i ) );
        System.out.println();


        String [] columns={"name","pontos"};

        String [][] linha= new String[5][5];

        DefaultTableModel table = new DefaultTableModel();
            table.addColumn("index");
            table.addColumn("Name");
            table.addColumn("pontos");


        String name;
        int point;
        int index=1;

        while (resultSet.next() )
        {

            name= resultSet.getString(1);
            point=resultSet.getInt(2);

            String []row={""+index++,name,""+point};
            table.addRow(row);


        }

        JFrame frame =new JFrame();
        frame.setLayout(new FlowLayout());

        JTable list= new JTable(linha,columns);


        list.setPreferredScrollableViewportSize(new Dimension(300,290));
        list.setFillsViewportHeight(true);

        list.setModel(table);
        JScrollPane scrollPane= new JScrollPane(list);


        frame.add(scrollPane);

        frame.setSize(500,290);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    public String maiorPontuacao() throws SQLException
    {
        String maior="0";

        Statement statement= con.createStatement();

        ResultSet resultSet =statement.executeQuery("SELECT * FROM pontuacao");

        int actualMaior=0;

        while (resultSet.next() )
        {


            int actual= Integer.parseInt(resultSet.getString(2));
                System.out.println(actual);

            if(actualMaior<actual)
                    actualMaior=actual;


        }
            maior=""+actualMaior;
        return maior;
    }


}


public class Main{
    public static void main(String [] args) throws SQLException {
     /*   Coneccao obj= new Coneccao();

        Connection j=obj.connectarBD();

        obj.adiconarTabela();
        obj.visualizarTable();

        obj.delectarTabela();

        obj.visualizarTable();
        */
        //Coneccao obj= new Coneccao();

       // Connection j=obj.connectarBD();

        //obj.selecionarTabela();


    }


}