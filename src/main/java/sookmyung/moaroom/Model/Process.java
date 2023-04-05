package sookmyung.moaroom.Model;

public enum Process {
    PROCEEDING(0), WAITING(1), GRADING(2), DONE(3);

    private int process_type;

    private Process(int code){
        this.process_type = code;
    }

    public int getRole(){
        return process_type;
    }

}
