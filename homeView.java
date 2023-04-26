import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class homeView extends JFrame implements ActionListener {

    Container container = getContentPane();
    // JFrame frame = new JFrame();
    JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("Reddit Clone");
    Member m1;
    conn connection;
    JTextArea textArea = new JTextArea();
    JScrollPane scroll = new JScrollPane(pnl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JButton membverView = new JButton("My Account");
    JButton createpost = new JButton("Create post");
    JButton logout = new JButton("Logout");
    JButton listMembers = new JButton("List Accounts");
    homeView(Member member,conn c1){
        this.connection = c1;
        m1 = member;
        setLayoutManager();
        setLocationAndSize();
        populatepost();
        addComponentsToContainer();
        addActionEvent();
        setTitle("Home Page");
        setVisible(true);
        setBounds(10, 10, 1100, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {

        label.setFont(new Font("Serif", Font.PLAIN, 30));
        label.setForeground(new java.awt.Color(0,0,0));
        label.setBackground(new java.awt.Color(255,67,0));
        label.setBounds(500,50,300,50);

        scroll.setBounds(50, 150, 1000, 500);
        membverView.setBounds(950, 50, 100, 40);
        
        Font fontt = new Font("Serif", Font.PLAIN, 15);
        membverView.setFont(fontt);
        createpost.setBounds(50,50, 150, 40);
        
        createpost.setFont(fontt);
        logout.setBounds(800, 50, 100,40);
        
        logout.setFont(fontt);
        listMembers.setBounds(150, 150, 100, 40);
        createpost.setForeground(new java.awt.Color(0,0,0));
        membverView.setForeground(new java.awt.Color(0,0,0));
        logout.setForeground(new java.awt.Color(0,0,0));
        createpost.setBackground(new java.awt.Color(255, 67,0));
        membverView.setBackground(new java.awt.Color(255, 67,0));
        logout.setBackground(new java.awt.Color(255, 67,0));
        listMembers.setFont(fontt);
        
    }

    public void addComponentsToContainer() {
        container.add(label);
        container.add(scroll);
        container.add(membverView);
        container.add(createpost);
        container.add(logout);
        if(m1.isAdmin || m1.isModerator){
            container.add(listMembers);
        }
    }

    public void populatepost() {
        ArrayList<Post> posts = new ArrayList<Post>();
        try{
          posts = add_posts(posts);
        }
        catch(Exception err){
          System.err.println(err);
        }


        for (int i = 0; i < posts.size(); i++) {
            // System.out.println("Hi!");
            System.out.print(posts.get(i).title);
            JLabel postLabel = new JLabel(posts.get(i).title);
            Post qs = posts.get(i);
            postLabel.setFont(new Font("Serif", Font.PLAIN, 20));
            postLabel.setBounds(150,(i+2)*100,800,30);
            container.add(postLabel);

            JLabel postDes = new JLabel(posts.get(i).caption);
            postDes.setFont(new Font("Serif", Font.PLAIN, 20));
            postDes.setBounds(150,(i+2)*100 + 50,800,30);

            container.add(postDes);

            JLabel postVote = new JLabel(Integer.toString(posts.get(i).voteCount)+"\r\r\n votes");
            postVote.setBounds(70,(i+2)*100,80,60);
            Font font = new Font("Serif", Font.PLAIN, 20);
            postVote.setFont(font);
            container.add(postVote);

            JButton button = new JButton("View post");
            button.setBounds(800,(i+2)*100+5,150,30);
            button.setForeground(new java.awt.Color(0,0,0));
            button.setBackground(new java.awt.Color(255, 67,0));
            Font fontt = new Font("Serif", Font.PLAIN, 20);
            button.setFont(fontt);
            container.add(button);

            button.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent e){
                dispose();
                new showyourpostView(qs, connection);
                
              }
            });
        }
    }

    public void addActionEvent() {
        // button.addActionListener(this);
        membverView.addActionListener(this);
    //     resetButton.addActionListener(this);
    createpost.addActionListener(this);

    logout.addActionListener(this);
    listMembers.addActionListener(this);
    //     showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("View post");

        if (e.getSource() == membverView) {
            // new memberView();
            // System.out.println("Member View");
            setVisible(false);
            dispose();
            showAccountView sav = new showAccountView(m1, connection);
            sav.setVisible(true);
        }
        if(e.getSource()==createpost){
            this.setVisible(false);
            this.dispose();
            createPostView cqv = new createPostView(new Post("", ""), m1, connection);
            cqv.setVisible(true);
        }
        if(e.getSource()==logout){
          this.setVisible(false);
          this.dispose();
          try{
            this.logout();
          }
          catch(Exception err){
            System.err.println(err);
          }
            new registrationView(connection);
        }
    }

    public ArrayList<Post> add_posts(ArrayList<Post> posts) throws Exception {
      String query = "SELECT * FROM post;";
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
          ResultSet rs = statement.executeQuery(query);
          while(rs.next()){
            
            Post q  = new Post(rs.getString("title"), rs.getString("caption"), rs.getInt("voteCount"));
            System.out.print(q.title);
            q.postid = rs.getString("postid");
            q.memid = rs.getString("memid");
            posts.add(q);
          }
          rs.close();
          statement.close();
          return posts;
      }
      catch(Exception e){
          throw e;
      }

    }

    public void logout() throws Exception{
      String query = "delete from currentmemid;";
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
      }
      catch(Exception e){
        throw e;
      }
}
}
