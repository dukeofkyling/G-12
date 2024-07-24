package Only.Server;




public class Applicant {
    private String name;
    private boolean isConfirmed;

    public Applicant(String name) {
        this.name = name;
        this.isConfirmed = false; // By default, applicant is not confirmed
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    @Override
    public String toString() {
        return name + (isConfirmed ? " - Confirmed" : " - Not Confirmed");
    }
}



