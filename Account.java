import java.util.UUID;
public class Account {
    protected String name;
    protected String password;
    protected String email;
    protected enum accStatus {
        ACTIVE,
        BLOCKED
    };
    protected accStatus status;
    protected String phone;
    String memid;
    public int reputation;
    boolean isModerator;
    boolean isAdmin;

    
    public Account(String name, String password, String email, String phone) {
        this.memid = "Mem"  + UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.email = email;
        this.status = accStatus.ACTIVE;
        this.phone = phone;
        this.reputation = 0;
        this.isModerator = false;
        this.isAdmin = false;
    }

    public Account(String name, String password, String email, String phone, String memid, boolean isModerator, boolean isAdmin, boolean acc_blocked, int reputation) {
        this.memid = memid;
        this.name = name;
        this.password = password;
        this.email = email;
        if(acc_blocked){
            this.status = accStatus.BLOCKED;
        }
        else{
            this.status = accStatus.ACTIVE;
        }
        this.phone = phone;
        this.reputation = reputation;
        this.isModerator = isModerator;
        this.isAdmin = isAdmin;
    }

    public boolean resetPassword(String password) {
        this.password = password;
        return true;
    }

    public boolean checkModerator() {
        if(this.reputation>50){
            this.isModerator = true;
            return true;
        }
        else{
            this.isModerator = false;
        }
        return false;
    }

    public boolean checkAdmin() {
        if(this.reputation>100){
            this.isModerator = true;
            this.isAdmin = true;
            return true;
        }
        else{
            this.isAdmin =false;
            this.checkModerator();
        }
        return false;
    }
}

