 public class WorkTime {
     // 開始時間, 終了時間
     private String startTime, endTime;
     
     public WorkTime(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;        
    }
     
     public String getStartTime() {
         return startTime;
     }
     
     public String getEndTime() {
         return endTime;
     }
}
