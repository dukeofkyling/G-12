package Only;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class Applicant {
    String name;
    boolean isConfirmed;

    public Applicant (String name) {
        this.name = name;
        this.isConfirmed = false; // Default status is "not confirmed"
    }

    @Override
    public String toString() {
        return name + (isConfirmed ? " - Confirmed" : " - Not Confirmed");
    }
}

public class ViewApplicants {

    private static final List<Applicant> applicants = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nSchool Challenge Application Management");
            // System.out.println("1. Add New Applicant");
            System.out.println("1. View All Applicants");
            System.out.println("2. Update Applicant Status");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                // case 1:
                //     // addNewApplicant();
                //     break;
                case 1:
                    viewAllApplicants();
                    break;
                case 2:
                    updateApplicantStatus();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    // private static void addNewApplicant() {
    //     System.out.print("Enter applicant's name: ");
    //     String name = scanner.nextLine();
    //     applicants.add(new Applicant(name));
    //     System.out.println("Applicant added successfully.");
    // }

    private static void viewAllApplicants() {
        System.out.println("Current Applicants:");
        for (Applicant applicant : applicants) {
            System.out.println(applicant);
        }
    }

    private static void updateApplicantStatus() {
        System.out.print("Enter applicant's name to update status: ");
        String name = scanner.nextLine();

        for (int i = 0; i < applicants.size(); i++) {
            if (applicants.get(i).name.equals(name)) {
                System.out.println("Found applicant: " + name);
                System.out.println("1. Confirm");
                System.out.println("2. Reject");
                int action = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                if (action == 1) {
                    applicants.get(i).isConfirmed = true;
                    System.out.println("Applicant confirmed.");
                } else if (action == 2) {
                    applicants.get(i).isConfirmed = false;
                    System.out.println("Applicant rejected.");
                } else {
                    System.out.println("Invalid action. No changes made.");
                }
                break;
            }
        }
    }
}

