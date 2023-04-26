import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class registrationView extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel label = new JLabel("Reddit Clone");
    conn connection;
    JLabel register = new JLabel("Register");
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel emailLabel = new JLabel("Email");
    JLabel phoneLabel = new JLabel("Phone");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JTextField emailTextField = new JTextField();
    JTextField phoneTextField = new JTextField();
    JButton registerButton = new JButton("Register");
    JCheckBox showPassword = new JCheckBox("Show Password");

    JLabel login = new JLabel("Login");
    JLabel loginUser = new JLabel("Username ");
    JLabel loginPassword = new JLabel("Password ");
    JTextField loginTextField = new JTextField();
    JPasswordField loginPasswordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JCheckBox loginShowPassword = new JCheckBox("Show Password");


    registrationView(conn c1) {
        this.connection = c1;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setTitle("Account Information");
        setVisible(true);
        setBounds(10, 10, 1100, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        label.setFont(new Font("Serif", Font.PLAIN, 28));
        label.setForeground(new java.awt.Color(0,0,0));
        label.setBackground(new java.awt.Color(255, 255,255));
        register.setFont(new Font("Serif", Font.PLAIN, 28));
        
        register.setForeground(new java.awt.Color(0,0,0));
        register.setBackground(new java.awt.Color(255, 255,255));
        login.setFont(new Font("Serif", Font.PLAIN, 28));
        
        login.setForeground(new java.awt.Color(0,0,0));
        login.setBackground(new java.awt.Color(255, 255,255));
        
        
        label.setBounds(500,30,300,50);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        register.setFont(new Font("Serif", Font.PLAIN, 28));
        userLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        emailLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        phoneLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        showPassword.setFont(new Font("Serif", Font.PLAIN, 17));
        register.setBounds(200,120,150,50);
        userLabel.setBounds(150, 250, 100, 30);
        passwordLabel.setBounds(150, 320, 100, 30);
        emailLabel.setBounds(150, 390, 100, 30);
        phoneLabel.setBounds(150, 460, 100, 30);
        userTextField.setBounds(250, 250, 150, 30);
        passwordField.setBounds(250, 320, 150, 30);
        emailTextField.setBounds(250, 390, 150, 30);
        phoneTextField.setBounds(250, 460, 150, 30);
        showPassword.setBounds(200, 550, 150, 30);
        registerButton.setBounds(170, 600, 200, 30);

        login.setBounds(750,250,150,50);
        loginUser.setBounds(650,350,100,30);
        loginPassword.setBounds(650,420,100,30);
        loginTextField.setBounds(750,350, 150, 30);
        loginPasswordField.setBounds(750,420,150,30);
        loginShowPassword.setBounds(700,500,150,30);
        loginButton.setBounds(670,550,200,30);
        loginButton.setForeground(new java.awt.Color(0,0,0));
        registerButton.setForeground(new java.awt.Color(0,0,0));
        loginButton.setBackground(new java.awt.Color(255, 67,0));
        registerButton.setBackground(new java.awt.Color(255, 67,0));
        loginUser.setFont(new Font("Serif", Font.PLAIN, 20));
        loginPassword.setFont(new Font("Serif", Font.PLAIN, 20));
        loginShowPassword.setFont(new Font("Serif", Font.PLAIN, 17));
    }

    public void addComponentsToContainer() {
        container.add(label);
        container.add(register);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(emailLabel);
        container.add(phoneLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(emailTextField);
        container.add(phoneTextField);
        container.add(showPassword);
        container.add(registerButton);

        container.add(login);
        container.add(loginUser);
        container.add(loginPassword);
        container.add(loginTextField);
        container.add(loginPasswordField);
        container.add(loginShowPassword);
        container.add(loginButton);
    }

    public void addActionEvent() {
        registerButton.addActionListener(this);
        showPassword.addActionListener(this);
        loginShowPassword.addActionListener(this);
        loginButton.addActionListener(this);
    }

    public void showMessage(String msg){
            JOptionPane.showMessageDialog(this, msg);
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == registerButton) {
            // userTextField.getText();
            String name = userTextField.getText();
            String password = passwordField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();
            Member account = new Member(name, password, email, phone);
            try{
              if(!(addaccount(account) == true)){
                showMessage("Account already exists");
            }
            else{
                homeView memberView = new homeView(account, connection);
                this.setVisible(false);
                memberView.setVisible(true);
                this.dispose();
            }
        }
          catch(Exception err){
            System.err.println(err);
          }
            
        }
        if (e.getSource() == loginButton) {
          try{
            Member m = checkLogin(loginTextField.getText(), loginPasswordField.getText());
            if(m == null){
                showMessage("Invalid username and/or password!");
            }
            else if(m.status == Account.accStatus.BLOCKED){
                showMessage("Your account is blocked!");
            }
            else{
                homeView meberView = new homeView(m, connection);
                this.setVisible(false);
                meberView.setVisible(true);
                this.dispose();
            }
        }
        catch(Exception err){
          System.err.println(err);
        }
          
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

        if (e.getSource() == loginShowPassword) {
            if (loginShowPassword.isSelected()) {
                loginPasswordField.setEchoChar((char) 0);
            } else {
                loginPasswordField.setEchoChar('*');
            }
        }

        }
        public boolean addaccount(Member acc) throws Exception {
          String query2 = "Select * from users where name=\'"+acc.name+"\';";
          String memid = UUID.randomUUID().toString();
          String query = "INSERT INTO users VALUES (\'" + acc.name + "\',\'"+acc.password +"\',\'" + acc.email +"\',\'"+ acc.phone+"\',\'" + acc.memid + "\'," + false + "," + acc.reputation + "," + acc.isModerator + "," + acc.isAdmin + ");";
          String query3 = "delete from currentmemid;";
          String query4 = "insert into currentmemid values(\'" + memid + "\');";
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
              ResultSet set = statement.executeQuery(query2);
              while(set.next()){
                return false;
              }
              statement = connection.createStatement();
              statement.executeUpdate(query);

              statement = connection.createStatement();
              statement.executeUpdate(query3);

              statement = connection.createStatement();
              statement.executeUpdate(query4);

              statement.close();
              connection.c.commit();
              return true;
          }
          catch(Exception e){
              throw e;
          }

        }

        public Member checkLogin(String name, String pass) throws Exception {
          String query2 = "Select * from users where name=\'"+name+"\' and password=\'"+pass+"\';";
          String query3 = "delete from currentmemid;";
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
              ResultSet set = statement.executeQuery(query2);
              if(set.next()){
                String memid = set.getString("memid");
                Member m = new Member(name, pass, set.getString("email"), set.getString("phone"), memid, 
                set.getBoolean("isModerator"), set.getBoolean("isAdmin"), set.getBoolean("acc_blocked"), set.getInt("reputation"));
                statement = connection.createStatement();
                statement.executeUpdate(query3);
                statement = connection.createStatement();
                statement.executeUpdate("Insert into currentmemid values(\'" + memid + "\');");
                statement.close();
                connection.c.commit();
                return m;
              }
              else{
                statement.close();
                return null;
              }
            }
            catch(Exception e){
                throw e;
            }
        }
    }