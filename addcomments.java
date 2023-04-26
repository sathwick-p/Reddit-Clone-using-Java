import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class addcomments extends JFrame implements ActionListener{
    Container container = getContentPane();
    Post post;
    Member m;
    String user;
    String pass;
    String connectionLink;
    JLabel comment_label = new JLabel("Comment Section");
    // JLabel commentcaptionLabel = new JLabel("Comment");
    JTextArea comment_text = new JTextArea();
    // JScrollPane comment_scroll = new JScrollPane (comment_text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JButton postcomment = new JButton("Post Comment");
    conn connection;

    addcomments(Post post, Member m, conn c1){
        this.post = post;
        this.m = m;
        this.connection = c1;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

        setTitle("Write your post");
        setVisible(true);
        setBounds(10, 10, 600, 600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        comment_text.setLineWrap(false);
        comment_text.setEditable(true);
        comment_text.setVisible(true);

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
      comment_label.setFont(new Font("Serif", Font.PLAIN, 25));
      comment_label.setForeground(new java.awt.Color(0, 0, 0));
      comment_label.setBackground(new java.awt.Color(255, 255, 255));
      postcomment.setForeground(new java.awt.Color(0, 0, 0));
      postcomment.setBackground(new java.awt.Color(255, 69, 0));

    //   commentcaptionLabel.setFont(new Font("Serif", Font.PLAIN, 24));
    //   commentcaptionLabel.setForeground(new java.awt.Color(255,165,0));
      comment_label.setBounds(200,30,200,100);
    //   commentcaptionLabel.setBounds(200, 130, 100, 100);
      comment_text.setBounds(150, 170, 300, 300);
      postcomment.setBounds(200, 120, 150, 30);
    }

    public void addComponentsToContainer() {
        container.add(comment_label);
        // container.add(commentcaptionLabel);
        container.add(comment_text);
        container.add(postcomment);
    }

    public void addActionEvent() {
        postcomment.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==postcomment){
            Comment ans = new Comment(comment_text.getText());
            post.addComment(ans);
            try{
              addcomment(ans, post.postid, m.memid);
            }
            catch(Exception err){
              System.err.println(err);
            }
            this.dispose();
        }
    }

    public void addcomment(Comment com, String postid, String memid) throws Exception {
        String query = "INSERT INTO Comments VALUES (\'" + postid + "\',\'"+ memid + "\',\'" + com.text +"\'," + com.voteCount + ");";
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
    }