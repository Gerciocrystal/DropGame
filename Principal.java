

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Random;

class Arquitetura extends JPanel{

    public void paintComponent(Graphics g){

        g.setColor(new Color(100,204,232));
        g.fillRect(0,0,400,100);

    }
}
class Jogo extends JFrame implements ActionListener,KeyListener{
    private JButton start,btnDrop;
    private Arquitetura obj;
    private JLabel score,lCurrent,l_Score;
    private JTextField scoreCount,tCurrent,t_Score;
    private Timer time;
    private int vez=0;
    private int level=0;
    private int levelScore=0,acompanhante=0;
    private int tsegundos=10;
    private int best_greed=0;

    private  int tempo_decrescente=300;
    private String pointName;

    Coneccao con= new Coneccao();


    public Jogo() throws Exception {

        con.connectarBD();

        best_greed= Integer.parseInt(con.maiorPontuacao());


        this.setLayout(null);
        this.setBackground(new Color(198,201,229));
        obj= new Arquitetura();
        obj.setBounds(0,300,400,100);



        start= new JButton("start");
        start.setBounds(0,270,120,30);
        start.setBackground(new Color(24,144,232));
        start.setForeground(Color.WHITE);

        start.addActionListener(this);
        start.addKeyListener(this);

        btnDrop= new JButton("a");
        btnDrop.setFont(new Font("Showcard Gothic",Font.PLAIN,19));
        btnDrop.setBounds(120,0,60,30);
        btnDrop.setBackground(new Color(24,144,232));
        btnDrop.setForeground(Color.WHITE);


        TimeHendler timeHendl= new TimeHendler();
        time= new Timer(tempo_decrescente, timeHendl);




        score= new JLabel("Level");
        score.setFont(new Font("Showcard Gothic",Font.PLAIN,19));
        score.setForeground(Color.WHITE);

        scoreCount= new JTextField("0",10);
        scoreCount.setBackground(new Color(202,214,230));
        scoreCount.setForeground(Color.WHITE);
        scoreCount.setFont(new Font("serif",Font.PLAIN,23));

        lCurrent= new JLabel("actual");
        lCurrent.setFont(new Font("Showcard Gothic",Font.PLAIN,19));
        lCurrent.setForeground(Color.WHITE);

        tCurrent= new JTextField("0",10);
        tCurrent.setBackground(new Color(202,214,230));
        tCurrent.setForeground(Color.WHITE);
        tCurrent.setFont(new Font("serif",Font.PLAIN,23));

        l_Score= new JLabel("Melhor");
        l_Score.setFont(new Font("Showcard Gothic",Font.BOLD,20));
        l_Score.setForeground(new Color(200,100,172));

        t_Score= new JTextField(""+best_greed,10);
        t_Score.setBackground(new Color(202,214,230));
        t_Score.setForeground(new Color(200,100,172));
        t_Score.setFont(new Font("serif",Font.PLAIN,23));

        JPanel title= new JPanel();
        title.setLayout(null);
        score.setBounds(20,10,58,20);
        scoreCount.setBounds(25,35,60,25);
        lCurrent.setBounds(15,95,78,20);
        tCurrent.setBounds(25,115,60,25);
        l_Score.setBounds(20,170,68,20);
        t_Score.setBounds(25,190,60,25);

        title.add(lCurrent);
        title.add(scoreCount);

        title.add(score);
        title.add(tCurrent);

        title.add(l_Score);
        title.add(t_Score);

        title.add(start);
        title.setBounds(280,0,120,300)    ;
        title.setBackground(new Color(131,179,242));


        this.add(title);
        this.add(obj);
        this.add(btnDrop);

        this.setLocation(455,180);
        this.setSize(400,400);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==start){

            if( vez==0){
                time.start();
                vez=1;
                start.setText("pause");

            }
            else{
                time.stop();
                vez=0;
                start.setText("start");
            }

        }



    }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){

        String anterior=btnDrop.getText();
        String  x= e.getKeyChar()+"";


        if(x.equals(anterior)){

            if(levelScore>=50){

                level+=1;
                scoreCount.setText(level+"");
                levelScore=0;

                tempo_decrescente-=50;
                time.stop();
                time.start();

                tsegundos+=3;

                btnDrop.setBackground(new Color(34,154,242))   ;
            }
            Random random = new Random();
            char randomizedCharacter = (char) (random.nextInt(26) + 'a');
            btnDrop.setText(randomizedCharacter+"");

            if(best_greed<acompanhante)
                best_greed= acompanhante;


            t_Score.setText(best_greed+"");

            levelScore+=10;
            acompanhante+=10;
            btnDrop.setLocation(btnDrop.getX(),0);
            tCurrent.setText(levelScore+"");
        }

    }
    class TimeHendler implements ActionListener{
        public void actionPerformed(ActionEvent e) {


            if(btnDrop.getY()>=300){



                btnDrop.setLocation(btnDrop.getX(),0);
                time.stop();
                JOptionPane.showMessageDialog(null,"GAME OVER")  ;
                try {
                    pointName= JOptionPane.showInputDialog(null,"entra com seu nome");
                    con.addicionarTabela(pointName,acompanhante);

                    con.selecionarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
                tsegundos=10;

                levelScore=0;
                acompanhante=0;


                level=0;
                scoreCount.setText(level+"");

                vez=0;


                System.out.print(pointName);

            }
            else
                btnDrop.setLocation(btnDrop.getX(),btnDrop.getY()+tsegundos);
        }
    }
    public void keyReleased(KeyEvent e){}
}
public class Principal{
    public static void main(String [] args) throws  Exception{
        Jogo obj= new Jogo();
    }
}