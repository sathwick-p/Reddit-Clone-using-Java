import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class viewcomments extends JFrame implements ActionListener{
    Container container = getContentPane();
    Post post;
    Member m;
    conn connection;
    JLabel label = new JLabel("Comment Section");
    JButton button;

    viewcomments(Post post, Member m, conn c1){
        this.post = post;
        this.m = m;
        this.connection = c1;
        setLayoutManager();
        setLocationAndSize();
        setTitle("View comments");
        setVisible(true);
        setBounds(10, 10, 600, 1000);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        ;
    }

    public void setLocationAndSize() {
      label.setFont(new Font("Serif", Font.PLAIN, 28));
      label.setForeground(new java.awt.Color(0,0,0));
      label.setBackground(new java.awt.Color(255, 255, 255));

      label.setBounds(200,30,300,50);
      container.add(label);
      JButton button;

      for(int i=0;i<post.comments.size();i++){
        // System.out.println("Hi");
        JLabel comment = new JLabel(post.comments.get(i).text);
        comment.setBounds(100,100 * i +100,300,30);
        container.add(comment);
        JLabel vote = new JLabel(Integer.toString(post.comments.get(i).voteCount));
        vote.setBounds(250,100 * i + 130,50,30);
        container.add(vote);

        Comment ans = post.comments.get(i);
        button = new JButton("Upvote");
        button.setFont(new Font("Serif", Font.PLAIN, 22));
  


        button.setForeground(new java.awt.Color(0,0,0));

  
        button.setBackground(new java.awt.Color(255,69,0));
        button.setBounds(100,100 * i + 160,100,30);
        button.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e){
            ans.incrementVoteCount();
            try{

                updatecommentVote(ans, post.postid);
            }
            catch(Exception err){
              System.err.println(err);
            }
            dispose();
            new viewcomments(post, m, connection);
          }
        });
        container.add(button);

        button = new JButton("Downvote");
        button.setFont(new Font("Serif", Font.PLAIN, 22));
  


        button.setForeground(new java.awt.Color(0,0,0));

  
        button.setBackground(new java.awt.Color(255,69,0));
 

        button.setBounds(300,100 * i + 160,150,30);
        button.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e){
            ans.decrementVoteCount();
            try{

                updatecommentVote(ans, post.postid);
            }
            catch(Exception err){
              System.err.println(err);
            }
            dispose();
            new viewcomments(post, m, connection);
          }
        });
        this.add(button);
        }
    }


    public void updatecommentVote(Comment com, String postid) throws Exception {
        String query = "UPDATE Comments set voteCount="+com.voteCount + "where postid=" + postid+";";
        System.out.println(query);
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.err.println (e);
            System.exit (-1);
        }
        try {
            connection.c.setAutoCommit(false);
            Statement statement = connection.createStatement ();
            statement.executeUpdate(query);
            statement.close();
            connection.c.commit();
        }
        catch(Exception e){
            throw e;
        }
  
      }
  
      // public void updateanswerVote(Answer com, String postid) throws Exception {
      //   String query = "UPDATE Answers set voteCount="+com.voteCount + "where postid=\'" + postid+"\';";
      //   System.out.println(query);
      //   try {
      //       Class.forName("org.postgresql.Driver");
      //   }
      //   catch (ClassNotFoundException e) {
      //       System.err.println (e);
      //       System.exit (-1);
      //   }
      //   try {
      //       connection.c.setAutoCommit(false);
      //       Statement statement = connection.createStatement ();
      //       statement.executeUpdate(query);
      //       statement.close();
      //       connection.c.commit();
      //   }
      //   catch(Exception e){
      //       throw e;
      //   }
  
      // }
    }