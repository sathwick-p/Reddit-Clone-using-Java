import java.util.ArrayList;

public class Member extends Account {
    public ArrayList<Post> posts;

    public Member(String name, String password, String email, String phone, String memid, boolean isModerator, boolean isAdmin, boolean acc_blocked, int reputation) {
        super(name, password, email, phone, memid, isModerator, isAdmin, acc_blocked, reputation);
        this.posts = new ArrayList<Post>();
    }

    public Member(String name, String password, String email, String phone) {
        super(name, password, email, phone);
        this.posts = new ArrayList<Post>();
    }

    

    public String getEmail() {
        return this.email;
    }

    public boolean createpost(Post ques) {
        this.posts.add(ques);
        return true;
    }

    public int getReputation() {
        System.out.println(this.reputation);
        return this.reputation;
    }

    public void incReputation(){
        this.reputation = this.reputation + 1;
        if(!this.isAdmin){
            if(!this.checkAdmin()){
                this.checkModerator();
            }
        }
    }

    public void decReputation(){
        this.reputation = this.reputation - 1;
        if(!this.isAdmin){
            if(!this.checkAdmin()){
                this.checkModerator();
            }
        }
    }

    public boolean blockMember(Member member){
        if(this.isAdmin && member.status == Member.accStatus.ACTIVE){
            member.status = Member.accStatus.BLOCKED;
            return true;
        }
        return false;
    }

    public boolean unblockMember(Member member){
        if(this.isAdmin && member.status == Member.accStatus.BLOCKED){
            member.status = Member.accStatus.ACTIVE;
            return true;
        }
        return false;
    }

    public boolean closePost(Post ques){
        if(this.isModerator && ques.status == PostStatus.Open){
            ques.status =PostStatus.Closed;
            return true;
        }
        return false;
    }

    public boolean openPost(Post ques){
        if(this.isModerator && ques.status == PostStatus.Closed){
            ques.status = PostStatus.Open;
            return true;
        }
        return false;
    }
}
