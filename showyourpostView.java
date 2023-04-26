import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class showyourpostView extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel label = new JLabel("Reddit Clone");
    Post post;
    JLabel Label_title = new JLabel("View Posts");
    JLabel title = new JLabel("Title");
    JLabel captionLabel = new JLabel("Text");
    JLabel titleField;
    JTextArea captionField;
    JLabel name = new JLabel("Posted by");
    JLabel nameField;
    JLabel vote = new JLabel("Votes");
    JLabel voteField;
    conn connection;
    // JButton AddAnswerButton = new JButton("Add Answer");
    // JButton ViewAnswers = new JButton("View Answers");
    JButton ViewComments = new JButton("View Comments");
    JButton AddCommentButton = new JButton("Add Comments");
    JButton backButton = new JButton("Back");
    JButton logoutButton = new JButton("Logout");
    JButton upvote = new JButton("Upvote");
    JButton downvote = new JButton("Downvote");
    JScrollPane scroll;
    Member m;

    showyourpostView(Post post, conn c1) {

        this.post = post;
        this.connection = c1;
        this.m = null;
        try{
          m = this.getMember();
        }
        catch(Exception err){
          System.err.println(err);
        }
        try{
          getcomment();
          // getanswer();
        }
        catch(Exception err){
          System.err.println(err);
        }
        titleField = new JLabel(this.post.title);
        captionField = new JTextArea(this.post.caption);
        scroll = new JScrollPane (captionField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nameField = new JLabel(m.name);
        voteField = new JLabel(Integer.toString(this.post.voteCount));
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

        setTitle("post");
        setVisible(true);
        setBounds(10, 10, 1100, 1000);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(true);
        captionField.setEditable(false);
        captionField.setVisible(true);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        label.setFont(new Font("Serif", Font.PLAIN, 25));
        label.setForeground(new java.awt.Color(0,0,0));
        label.setBackground(new java.awt.Color(255,255,255));

        Label_title.setFont(new Font("Serif", Font.PLAIN, 25));
        Label_title.setForeground(new java.awt.Color(0,0,0));
        Label_title.setBackground(new java.awt.Color(255,255,255));

        logoutButton.setFont(new Font("Serif", Font.PLAIN, 22));
        AddCommentButton.setFont(new Font("Serif", Font.PLAIN, 22));
        ViewComments.setFont(new Font("Serif", Font.PLAIN, 22));
        AddCommentButton.setFont(new Font("Serif", Font.PLAIN, 22));
        backButton.setFont(new Font("Serif", Font.PLAIN, 22));
        upvote.setFont(new Font("Serif", Font.PLAIN, 22));
        downvote.setFont(new Font("Serif", Font.PLAIN, 22));
        logoutButton.setForeground(new java.awt.Color(0,0,0));
        AddCommentButton.setForeground(new java.awt.Color(0,0,0));
        ViewComments.setForeground(new java.awt.Color(0,0,0));
        AddCommentButton.setForeground(new java.awt.Color(0,0,0));
        backButton.setForeground(new java.awt.Color(0,0,0));
        upvote.setForeground(new java.awt.Color(0,0,0));
        downvote.setForeground(new java.awt.Color(0,0,0));
        logoutButton.setBackground(new java.awt.Color(255,67,0));
        AddCommentButton.setBackground(new java.awt.Color(255,67,0));
        ViewComments.setBackground(new java.awt.Color(255,67,0));
        AddCommentButton.setBackground(new java.awt.Color(255,67,0));
        backButton.setBackground(new java.awt.Color(255,67,0));
        upvote.setBackground(new java.awt.Color(255,67,0));
        downvote.setBackground(new java.awt.Color(255,67,0));

        name.setFont(new Font("Serif", Font.PLAIN, 25));
        title.setFont(new Font("Serif", Font.PLAIN, 25));
        nameField.setFont(new Font("Serif", Font.PLAIN, 25));
        titleField.setFont(new Font("Serif", Font.PLAIN, 25));
        vote.setFont(new Font("Serif", Font.PLAIN, 25));
        voteField.setFont(new Font("Serif", Font.PLAIN, 25));
        captionLabel.setFont(new Font("Serif", Font.PLAIN, 25));

        label.setBounds(500,30,500,100);
        logoutButton.setBounds(900, 10, 150, 30);
        Label_title.setBounds(500,120,500,50);
        name.setBounds(300,200,200,30);
        title.setBounds(300, 250, 100, 30);
        vote.setBounds(300, 300, 100, 30);
        captionLabel.setBounds(300, 350, 700, 30);
        nameField.setBounds(450, 200, 500, 30);
        titleField.setBounds(400,250, 750, 30);
        voteField.setBounds(400, 300, 750, 30);
        scroll.setBounds(250, 400, 550, 400);
        // AddAnswerButton.setBounds(50, 500, 150, 30);
        AddCommentButton.setBounds(50, 600, 150, 30);
        // ViewAnswers.setBounds(50,10, 150, 30);
        ViewComments.setBounds(50, 60, 150, 30);
        backButton.setBounds(900,60,150,30);
        upvote.setBounds(900,500,150,30);
        downvote.setBounds(900, 600, 150, 30);
    }

    public void addComponentsToContainer() {
        container.add(label);
        container.add(Label_title);
        container.add(title);
        container.add(captionLabel);
        container.add(titleField);
        // container.add(AddAnswerButton);
        container.add(scroll);
        container.add(AddCommentButton);
        // container.add(ViewAnswers);
        container.add(ViewComments);
        container.add(backButton);
        container.add(name);
        container.add(nameField);
        container.add(vote);
        container.add(voteField);
        container.add(upvote);
        container.add(downvote);
        container.add(logoutButton);
    }

    public void addActionEvent() {
      // AddAnswerButton.addActionListener(this);
      AddCommentButton.addActionListener(this);
      // ViewAnswers.addActionListener(this);
      ViewComments.addActionListener(this);
      backButton.addActionListener(this);
      upvote.addActionListener(this);
      downvote.addActionListener(this);
      logoutButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      // if(e.getSource()== AddAnswerButton){
      //   // new addanswers(post, m, connection);
      // }
      if(e.getSource()==AddCommentButton){
        new addcomments(post, m, connection);
      }
      // if(e.getSource()==ViewAnswers){
      //   // new viewanswers(post, m, connection);
      // }
      if(e.getSource()==ViewComments){
        new viewcomments(post, m, connection);
      }
      if(e.getSource()==backButton){
        setVisible(false);
        new homeView(m, connection);
      }
      if(e.getSource()==upvote){
        try{
          this.post.incrementVoteCount();
          updatepostVote(post,1);
          upvote.setEnabled(false);
          downvote.setEnabled(true);
          this.dispose();
          new  showyourpostView(post, connection);
        }
        catch(Exception err){
          System.err.println(err);
        }
      }
      if(e.getSource()==downvote){
        try{
          this.post.decrementVoteCount();
          updatepostVote(post, 0);
          upvote.setEnabled(true);
          downvote.setEnabled(false);
          this.dispose();
          new showyourpostView(post, connection);
        }
        catch(Exception err){
          System.err.println(err);
        }
      }
    }

    public void updatepostVote(Post q, int voteType) throws Exception {
      String query ="UPDATE post SET voteCount=" + q.voteCount + " WHERE postid='" + q.postid + "';";
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

    // public void getanswer() throws Exception {
    //   String query = "SELECT * FROM Answers WHERE postid=\'"+post.postid+"\';";
    //   System.out.println(query);
    //   try {
    //       Class.forName("org.postgresql.Driver");
    //   }
    //   catch (ClassNotFoundException e) {
    //       System.err.println (e);
    //       System.exit (-1);
    //   }
    //   try {
    //       Statement statement = connection.createStatement ();
    //       ResultSet rs = statement.executeQuery(query);
    //       while(rs.next()){
    //         // this.post.addAnswer(new Answer(rs.getString("answer_text"), rs.getInt("votecount")));
    //       }
    //       rs.close();
    //       statement.close();
    //   }
    //   catch(Exception e){
    //       throw e;
    //   }

    // }

    public void getcomment() throws Exception {
      String query = "SELECT * FROM Comments WHERE postid=\'"+post.postid+"\';";
      System.out.println(query);
      try {
          Class.forName("org.postgresql.Driver");
      }
      catch (ClassNotFoundException e) {
          System.err.println (e);
          System.exit (-1);
      }
      try {
          Statement statement = connection.createStatement ();
          ResultSet rs = statement.executeQuery(query);
          while(rs.next()){
            this.post.addComment(new Comment(rs.getString("text"), rs.getInt("votecount")));
          }
          rs.close();
          statement.close();
      }
      catch(Exception e){
          throw e;
      }

    }

    public Member getMember() throws Exception{
      String query = "SELECT * FROM users WHERE memid=\'"+post.memid+"\';";
      System.out.println(query);
      try {
          Class.forName("org.postgresql.Driver");
      }
      catch (ClassNotFoundException e) {
          System.err.println (e);
          System.exit (-1);
      }
      try {
          Statement statement = connection.createStatement ();
          ResultSet set = statement.executeQuery(query);
          if(set.next()){
            Member m = new Member(set.getString("name"), set.getString("password"), set.getString("email"), set.getString("phone"), set.getString("memid"), 
            set.getBoolean("isModerator"), set.getBoolean("isAdmin"), set.getBoolean("acc_blocked"), set.getInt("reputation"));
          set.close();
          statement.close();
          return m;
          }
          else{
            set.close();
            statement.close();
            return null;
          }
      }
      catch(Exception e){
        throw e;
      }
    }
}
