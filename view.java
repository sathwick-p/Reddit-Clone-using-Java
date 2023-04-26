public class view {
    public static void main(String[] args) {
        // showAccountView theView = new showAccountView(member);
        // post post=new post("","");
        String connectionLink = "jdbc:postgresql://127.0.0.1:5432/reddit";
        String user = "postgres";
        String pass = "1234";
        conn c1 = conn.getInstance(connectionLink, user, pass);
        new registrationView(c1);
        // theView.setVisible(true);
    }
}
