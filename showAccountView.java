import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class showAccountView extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel emailLabel = new JLabel("Email");
    JLabel phoneLabel = new JLabel("Phone");
    // JLabel reputationsLabel = new JLabel("");
    JLabel userTextField = new JLabel();
    JPasswordField passwordField = new JPasswordField();
    JLabel emailTextField = new JLabel();
    JLabel phoneTextField = new JLabel();
    // JLabel reputationsTextField = new JLabel();
    JButton updateButton = new JButton("Update");
    JButton resetButton = new JButton("Reset");
    JCheckBox showPassword = new JCheckBox("Show Password");
    String connectionLink;
    String user;
    String pass;
    JButton backButton = new JButton("Back");
    Member mem;
    conn connection;
    JLabel postLabel = new JLabel("My Posts");
    // JTextArea textArea = new JTextArea("TEST");
    // JScrollPane scroll = new JScrollPane (textArea,
//    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);



    showAccountView(Member account, conn c1) {

        this.connection = c1;
        this.mem = account;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        populatepost();
        setTitle("Account Information");
        setVisible(true);
        setBounds(10, 10, 1100, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        userTextField.setText(account.name);
        passwordField.setText(account.password);
        emailTextField.setText(account.email);
        phoneTextField.setText(account.phone);
        // reputationsTextField.setText(Integer.toString(account.reputation));

        ;
        // textArea.setLineWrap(true);
        // textArea.setEditable(false);
        // textArea.setVisible(true);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        postLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        userLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        emailLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        phoneLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        userTextField.setFont(new Font("Serif", Font.PLAIN, 22));
        passwordField.setFont(new Font("Serif", Font.PLAIN, 22));
        emailTextField.setFont(new Font("Serif", Font.PLAIN, 22));
        phoneTextField.setFont(new Font("Serif", Font.PLAIN, 22));
        passwordField.setFont(new Font("Serif", Font.PLAIN, 22));
        
        
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 223, 100, 30);
        emailLabel.setBounds(50, 290, 100, 30);
        phoneLabel.setBounds(50, 360, 100, 30);
        // reputationsLabel.setBounds(50, 430, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 223, 150, 30);
        emailTextField.setBounds(150, 290, 150, 30);
        phoneTextField.setBounds(150, 360, 150, 30);

        
        // reputationsTextField.setBounds(150, 430, 150, 30);
        showPassword.setBounds(150, 500, 150, 30);
        updateButton.setBounds(50, 600, 100, 30);
        updateButton.setForeground(new java.awt.Color(0,0,0));
        resetButton.setForeground(new java.awt.Color(0,0,0));
        updateButton.setBackground(new java.awt.Color(255, 67, 0));
        resetButton.setBackground(new java.awt.Color(255, 67, 0));
        resetButton.setBounds(230, 600, 100, 30);
        backButton.setBounds(50,50,100,30);
        backButton.setForeground(new java.awt.Color(0,0,0));
        backButton.setBackground(new java.awt.Color(255, 67, 0));
        postLabel.setBounds(600, 100,500, 30);
        // scroll.setBounds(500, 150, 500, 800);
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(emailLabel);
        container.add(phoneLabel);
        // container.add(reputationsLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(emailTextField);
        container.add(phoneTextField);
        // container.add(reputationsTextField);
        container.add(showPassword);
        container.add(updateButton);
        container.add(resetButton);
        container.add(backButton);
        container.add(postLabel);
        // container.add(scroll);
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
            postLabel.setFont(new Font("Serif", Font.PLAIN, 23));
            postLabel.setBounds(550,(i+2)*100,400,30);
            container.add(postLabel);

            JLabel postDes = new JLabel(posts.get(i).caption);
            // postDes.setFont(new Font("Serif", Font.PLAIN, 23));
            postDes.setBounds(550,(i+2)*100 + 50,400,30);
            container.add(postDes);

            

            JButton button = new JButton("View post");
            button.setBounds(900,(i+2)*100+5,150,30);
            button.setForeground(new java.awt.Color(0,0,0));
            button.setBackground(new java.awt.Color(255, 67, 0));
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
        updateButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        backButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == updateButton) {
            updateDetails();
        }
        if (e.getSource() == resetButton) {
            resetPasswordFrame();
        }
        if (e.getSource() == backButton) {
          setVisible(false);
          dispose();
          homeView hv = new homeView(mem,connection);
          hv.setVisible(true);
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }


        }
    }

    public void resetPasswordFrame() {
        JFrame resetPassword = new JFrame("Reset Password");
        JLabel newPasswordLabel = new JLabel("New Password");
        JPasswordField newPasswordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        JPasswordField confirmPasswordField = new JPasswordField();
        JButton resetButton = new JButton("Reset");
        resetPassword.setLayout(null);
        resetPassword.setSize(400, 400);
        resetPassword.setVisible(true);
        resetPassword.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        resetPassword.setResizable(false);
        resetPassword.setLocationRelativeTo(null);
        newPasswordLabel.setBounds(50, 50, 100, 30);
        newPasswordField.setBounds(150, 50, 150, 30);
        confirmPasswordLabel.setBounds(50, 100, 100, 30);
        confirmPasswordField.setBounds(150, 100, 150, 30);
        resetButton.setBounds(150, 150, 100, 30);
        resetButton.setForeground(new java.awt.Color(0,0,0));
        resetButton.setBackground(new java.awt.Color(255, 67, 0));
        resetPassword.add(newPasswordLabel);
        resetPassword.add(newPasswordField);
        resetPassword.add(confirmPasswordLabel);
        resetPassword.add(confirmPasswordField);
        resetPassword.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newPasswordField.getText().equals(confirmPasswordField.getText())) {
                    passwordField.setText(newPasswordField.getText());
                    JOptionPane.showMessageDialog(resetPassword, "Password Changed Successfully");
                    resetPassword.dispose();
                } else {
                    JOptionPane.showMessageDialog(resetPassword, "Password Mismatch");
                }
            }
        });
    }

    public void updateDetails() {
        JFrame updateDetails = new JFrame("Update Details");
        JLabel newEmailLabel = new JLabel("New Email");
        JTextField newEmailField = new JTextField();
        JLabel newPhoneLabel = new JLabel("New Phone");
        JTextField newPhoneField = new JTextField();
        JButton button = new JButton("UPDATE");
        updateDetails.setLayout(null);
        updateDetails.setSize(400, 400);
        button.setForeground(new java.awt.Color(0,0,0));
        button.setBackground(new java.awt.Color(255, 67, 0));
        updateDetails.setVisible(true);
        updateDetails.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        updateDetails.setResizable(false);
        updateDetails.setLocationRelativeTo(null);
        newEmailLabel.setBounds(50, 50, 100, 30);
        newEmailField.setBounds(150, 50, 150, 30);
        newPhoneLabel.setBounds(50, 100, 100, 30);
        newPhoneField.setBounds(150, 100, 150, 30);
        button.setBounds(150, 150, 100, 30);
        updateDetails.add(newEmailLabel);
        updateDetails.add(newEmailField);
        updateDetails.add(newPhoneLabel);
        updateDetails.add(newPhoneField);
        updateDetails.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailTextField.setText(newEmailField.getText());
                phoneTextField.setText(newPhoneField.getText());
                emailTextField.setText(newEmailField.getText());
                phoneTextField.setText(newPhoneField.getText());
                JOptionPane.showMessageDialog(updateDetails, "Details Updated Successfully");
                updateDetails.dispose();
            }
        });

    }

    public ArrayList<Post> add_posts(ArrayList<Post> posts) throws Exception {
        String query = "SELECT * FROM Post where memid=\'" +mem.memid + "\';";
        //  WHERE memid=\'" + mem.memid + "\';";
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

}
