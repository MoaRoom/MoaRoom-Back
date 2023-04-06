package sookmyung.moaroom.Model;

public enum Role {
    STUDENT(0), ASSISTANT(1), PROFESSOR(2);

    private int role_type;

    private Role(int code){
        this.role_type = code;
    }

    public int getRole(){
        return role_type;
    }


}
