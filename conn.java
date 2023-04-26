import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn {
    public Connection c = null;
	public Statement stmt = null;
	private static conn instance;

	private conn(String connectionLink, String user, String pass)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(connectionLink, user, pass);
            System.out.println("Connected");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

    public static conn getInstance(String connectionLink, String user, String pass){
		if(instance == null){
			instance = new conn(connectionLink, user, pass);
		}
		return instance;
	}

    public Statement createStatement(){
        try{
            this.stmt = c.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
        }
            return this.stmt;
    }

    public void close(){
        try{
            stmt.close();
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
        }
    }
}

