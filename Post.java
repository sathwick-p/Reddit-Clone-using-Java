import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

enum PostStatus{
  Open, Closed
}

enum PostClosingRemark{
  Duplicate, OffTopic, TooBroad, NotConstructive, NotRealpost, OpinionBased
}

interface Search{
  void search(String query);
}

class Post{
  String postid;
  String title;
  String caption;
  int viewCount;
  int voteCount;
  int flagCount = 0;
  LocalTime createTime;
  PostClosingRemark closingRemark;
  PostStatus status;
  ArrayList<Comment> comments;
  // ArrayList<Answer> answers;
  ArrayList<Tag> tags;
  String memid;

  Post(String title, String caption){
    postid = UUID.randomUUID().toString();
    this.title = title;
    this.caption = caption;
    this.createTime = LocalTime.now();
    this.viewCount = 0;
    this.flagCount = 0;
    this.voteCount = 0;
    this.status = PostStatus.Open;
    comments = new ArrayList<Comment>();
    // answers = new ArrayList<Answer>();
    tags = new ArrayList<Tag>();
  }

  Post(String title, String caption, int voteCount){
    postid = UUID.randomUUID().toString();
    this.title = title;
    this.caption = caption;
    this.createTime = LocalTime.now();
    this.viewCount = 0;
    this.flagCount = 0;
    this.voteCount = voteCount;
    this.status = PostStatus.Open;
    comments = new ArrayList<Comment>();
    // answers = new ArrayList<Answer>();
    tags = new ArrayList<Tag>();
  }

  boolean close(PostClosingRemark remark){
    this.closingRemark = remark;
    this.status = PostStatus.Closed;
    return true;
  }

  void incrementVoteCount(){
    this.voteCount+=1;
  }

  void decrementVoteCount(){
    this.voteCount-=1;
  }

  void incrementViewCount(){
    this.viewCount+=1;
  }

  void addComment(Comment comment){
    this.comments.add(comment);
  }

  // void addAnswer(Answer answer){
  //   this.answers.add(answer);
  // }

  void addtags(Tag tag){
    this.tags.add(tag);
  }
}

class Comment{
  String text;
  LocalTime creationTime;
  int flagCount;
  int voteCount;

  Comment(String text){
    this.text = text;
    this.creationTime = LocalTime.now();
    this.flagCount = 0;
    this.voteCount = 0;
  }

  Comment(String text, int voteCount){
    this.text = text;
    this.creationTime = LocalTime.now();
    this.flagCount = 0;
    this.voteCount = voteCount;
  }

  void incrementVoteCount(){
    this.voteCount+=1;
  }
  void decrementVoteCount(){
    this.voteCount-=1;
  }
  void incrementFlagCount(){
    this.flagCount+=1;
  }
}

