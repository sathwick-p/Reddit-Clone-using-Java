import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createPostView extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel label = new JLabel("Reddit Clone");
    Post post;
    JLabel Label_title = new JLabel("Create a post");
    JLabel title = new JLabel("Title");
    JLabel captionLabel = new JLabel("Add Text");
    JTextField titleTextField = new JTextField();
    JTextArea captionTextField= new JTextArea();
    JButton PostButton = new JButton("Post");
    JScrollPane scroll = new JScrollPane (captionTextField,
    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    Member member;
    conn connection;
    createPostView(Post post, Member m, conn c1) {
        this.post = post;
        this.member = m;
        this.connection =c1;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("Create a post");
        setVisible(true);
        setBounds(10, 10, 1100, 800);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        captionTextField.setLineWrap(false);
        captionTextField.setEditable(true);
        captionTextField.setVisible(true);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        label.setFont(new Font("Serif", Font.PLAIN, 25));
        label.setForeground(new java.awt.Color(0,0,0));
        label.setBackground(new java.awt.Color(255,67,0));

        Label_title.setFont(new Font("Serif", Font.PLAIN, 25));
        Label_title.setForeground(new java.awt.Color(0,0,0));
        Label_title.setBackground(new java.awt.Color(255, 67,0));

        title.setFont(new Font("Serif", Font.PLAIN, 25));
        title.setForeground(new java.awt.Color(0,0,0));
        title.setBackground(new java.awt.Color(255, 67,0));

        captionLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        captionLabel.setForeground(new java.awt.Color(0,0,0));
        captionLabel.setBackground(new java.awt.Color(255, 67,0));
        PostButton.setFont(new Font("Serif", Font.PLAIN, 25));
        PostButton.setForeground(new java.awt.Color(0,0,0));
        PostButton.setBackground(new java.awt.Color(255, 67, 0));

        label.setBounds(500,30,700,100);
        Label_title.setBounds(500,120,500,50);
        title.setBounds(150, 250, 100, 30);
        captionLabel.setBounds(150, 340, 100, 30);
        titleTextField.setBounds(250,250, 750, 30);
        captionTextField.setBounds(10,10,500,30);
        scroll.setBounds(150, 370, 850, 400);
        PostButton.setBounds(500, 600, 150, 30);
        
    }

    public void addComponentsToContainer() {
        container.add(label);
        container.add(Label_title);
        container.add(title);
        container.add(captionLabel);
        container.add(titleTextField);
        container.add(PostButton);
        container.add(scroll);
        PostButton.setVisible(true);

    }

    public void addActionEvent() {
      PostButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource()== PostButton){
          try{
            this.post.title = titleTextField.getText();
            this.post.caption = captionTextField.getText();
            this.post.memid = member.memid;
            updatepost();
          }
          catch(Exception ex){
            System.err.println(ex);
          }
          this.setVisible(false);
          showyourpostView syqv = new showyourpostView(this.post, connection);
          syqv.setVisible(true);
          this.dispose();
      }
    }


    public void updatepost() throws Exception {
      String query = "INSERT INTO post VALUES (\'"+this.post.postid + "\',\'" + this.post.title +"\',\'"+this.post.caption+"\'," + this.post.voteCount+",\'" + this.member.memid + "\');";
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