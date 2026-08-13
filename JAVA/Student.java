import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of students to be added: ");
        int n = input.nextInt();
        input.nextLine(); // Consume newline

        // Initialize student array
        Student[] studentsArray = new Student[n]; // Array to store students

        for (int i = 0; i < studentsArray.length; i++) {
            studentsArray[i] = new Student();
            System.out.println("\nEnter the details of student [" + (i + 1) + "]: ");
            studentsArray[i].getStudentData();
        }

        int choice;
        do {
            System.out.println("\nMain menu");
            System.out.println("1. View Student Details");
            System.out.println("2. Search Student");
            System.out.println("3. Add Student");
            System.out.println("4. Remove Student");
            System.out.println("5. Update Student Details");
            System.out.println("6. Sort Students by Name");
            System.out.println("7. Filter Students by Course");
            System.out.println("8. View Student Report");
            System.out.println("9. Mark Attendance");
            System.out.println("10. View Attendance");
            System.out.println("11. Generate Attendance Report");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    for (int j = 0; j < studentsArray.length; j++) {
                        System.out.println("\nStudent [" + (j + 1) + "] details: ");
                        studentsArray[j].viewStudentDetails();
                    }
                    break;
                case 2:
                    Student.searchStudent(studentsArray);
                    break;
                case 3:
                    studentsArray = Student.addStudent(studentsArray);
                    break;
                case 4:
                    studentsArray = Student.removeStudent(studentsArray);
                    break;
                case 5:
                    Student.updateStudentDetails(studentsArray);
                    break;
                case 6:
                    Student.sortStudentsByName(studentsArray);
                    break;
                case 7:
                    Student.filterStudentsByCourse(studentsArray);
                    break;
                case 8:
                    Student.viewStudentReport(studentsArray);
                    break;
                case 9:
                    System.out.print("Enter student ID to mark attendance: ");
                    int markID = input.nextInt();
                    input.nextLine(); // Consume newline
                    for (Student student : studentsArray) {
                        if (student.studentID == markID) {
                            student.markAttendance(input);
                            break;
                        }
                    }
                    break;
                case 10:
                    System.out.print("Enter student ID to view attendance: ");
                    int viewID = input.nextInt();
                    input.nextLine(); // Consume newline
                    for (Student student : studentsArray) {
                        if (student.studentID == viewID) {
                            student.viewAttendance();
                            break;
                        }
                    }
                    break;
                case 11:
                    System.out.println("\n--- Attendance Report ---");
                    for (Student student : studentsArray) {
                        student.generateReport();
                    }
                    break;
                case 12:
                    System.out.println("Thank you, Exiting...");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1-12.");
            }
        } while (choice != 12);

        input.close();
    }
}

class Student {
    Scanner sc = new Scanner(System.in);
    String studentName;
    String studentAddress;
    int studentID;
    String courseEnrolled;

    // Fixed-size arrays to store attendance dates and status (backup approach)
    String[] attendanceDates = new String[30];  // Assuming a max of 30 attendance records
    String[] attendanceStatus = new String[30]; // Same here, max of 30 attendance records
    int currentIndex = 0;  // Index to track the number of attendance records (for fixed-size array)

    // Method to get student data
    void getStudentData() {
        System.out.print("\nEnter the name of student: ");
        studentName = sc.nextLine();
        System.out.print("Enter the address of student: ");
        studentAddress = sc.nextLine();
        System.out.print("Enter the ID of student: ");
        studentID = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter the course enrolled in: ");
        courseEnrolled = sc.nextLine();
    }

    // Method to view student details
    void viewStudentDetails() {
        System.out.println("\nStudent Name: " + studentName);
        System.out.println("Student Address: " + studentAddress);
        System.out.println("Student ID: " + studentID);
        System.out.println("Enrolled Course: " + courseEnrolled);
    }

    // Method to mark attendance for a student
    void markAttendance(Scanner scanner) {
        System.out.print("Enter date (1-30): ");
        int date = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (date < 1 || date > 30) {
            System.out.println("Invalid date! Please enter a valid date (1-30).");
            
        }

        System.out.print("Mark attendance for " + studentName + " (P/A): ");
        String status = scanner.nextLine().toUpperCase();
        if (status.equals("P") || status.equals("A")) {
            attendanceStatus[date - 1] = status;
        } else {
            System.out.println("Invalid input! Marking as absent by default.");
            attendanceStatus[date - 1] = "A";
        }
        System.out.println("Attendance marked successfully!");
    }

    // Method to view attendance for a student
    void viewAttendance() {
        System.out.println("\nAttendance for " + studentName + ":");
        for (int i = 0; i < 30; i++) {
            if (attendanceStatus[i] != null) {
                System.out.println("Day " + (i + 1) + ": " + attendanceStatus[i] + "  ");
            } else {
                System.out.println("Day " + (i + 1) + ": -  ");
            }
        }
        System.out.println();
    }

    // Method to generate attendance report for a student
    void generateReport() {
        int presentCount = 0, totalDays = 0;
        for (int i = 0; i < 30; i++) {
            if (attendanceStatus[i] != null) {
                totalDays++;
                if (attendanceStatus[i].equals("P")) {
                    presentCount++;
                }
            }
        }
        double percentage = (totalDays > 0) ? (presentCount * 100.0 / totalDays) : 0;
        System.out.println(studentName + " - Attendance: " + percentage + "%");
    }

    // Static method to search for a student
    static void searchStudent(Student[] studentArray) {
        String name;
        int ID;
        boolean found = false;
        int option;
        Scanner sc = new Scanner(System.in);

        System.out.println("\nHow do you want to search for the student?");
        System.out.println("1. By name");
        System.out.println("2. By ID");

        System.out.print("Enter your choice: ");
        option = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (option) {
            case 1:
                System.out.print("Enter the name of student: ");
                name = sc.nextLine();
				StringBuffer s=new StringBuffer(name);
                for (int i = 0; i < studentArray.length; i++) {
					
                    if (name.equalsIgnoreCase(studentArray[i].studentName)) {
						System.out.print("-----------Details of Student --------------");
                        studentArray[i].viewStudentDetails();
						found = true;
                        break;
                    }
					
                }
                if (!found) {
                    System.out.println("No student found with that name.");
                }
				
                break;
            case 2:
                System.out.print("Enter the ID of student: ");
                ID = sc.nextInt();
                for (int i = 0; i < studentArray.length; i++) {
                    if (ID == studentArray[i].studentID) {
						System.out.print("-----------Details of Student --------------");
                        studentArray[i].viewStudentDetails();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("No student found with that ID.");
                }
                break;

            default:
                System.out.println("Invalid input. Please enter a number between 1-2.");
        }
    }

    // Method to add a new student
    static Student[] addStudent(Student[] studentArray) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many students you want to add: ");
        int newStudentsCount = sc.nextInt();
        sc.nextLine(); // Consume newline

        Student[] newArray = new Student[studentArray.length + newStudentsCount];

        // Copy old students into new array
        for (int j = 0; j < studentArray.length; j++) {
            newArray[j] = studentArray[j];
        }

        // Add new students
        for (int i = studentArray.length; i < newArray.length; i++) {
            newArray[i] = new Student();
            System.out.println("Enter the details of new student [" + (i + 1) + "]: ");
            newArray[i].getStudentData();
        }

        System.out.println(newStudentsCount + " student(s) added.");
        return newArray;
    }

    // Method to remove a student by ID
    static Student[] removeStudent(Student[] studentArray) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student ID to remove: ");
        int ID = sc.nextInt();
        sc.nextLine(); // Consume newline

        int indexToRemove = -1;
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i].studentID == ID) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            System.out.println("Student with ID " + ID + " not found.");
        } else {
            Student[] newArray = new Student[studentArray.length - 1];
            for (int i = 0, j = 0; i < studentArray.length; i++) {
                if (i != indexToRemove) {
                    newArray[j++] = studentArray[i];
                }
            }
            System.out.println("Student with ID " + ID + " removed.");
            return newArray;
        }
        return studentArray; // Return unchanged array if no student removed
    }

    // Method to update student details
    static void updateStudentDetails(Student[] studentArray) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID of the student to update: ");
        int ID = sc.nextInt();
        sc.nextLine(); // Consume newline

        boolean found = false;
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i].studentID == ID) {
                System.out.println("Updating details for student: " + studentArray[i].studentName);
                System.out.print("Enter new name (or press Enter to keep current): ");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) studentArray[i].studentName = newName;
                System.out.print("Enter new address (or press Enter to keep current): ");
                String newAddress = sc.nextLine();
                if (!newAddress.isEmpty()) studentArray[i].studentAddress = newAddress;
                System.out.print("Enter new course (or press Enter to keep current): ");
                String newCourse = sc.nextLine();
                if (!newCourse.isEmpty()) studentArray[i].courseEnrolled = newCourse;
                System.out.println("Student details updated.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No student found with that ID.");
        }
    }

    // Method to sort students by name
    static void sortStudentsByName(Student[] studentArray) {
        int n = studentArray.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                // Compare students by name
                if (studentArray[j].studentName.compareTo(studentArray[j + 1].studentName) > 0) {
                    // Swap students[j] and students[j + 1] if they are out of order
                    Student temp = studentArray[j];
                    studentArray[j] = studentArray[j + 1];
                    studentArray[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped, the array is already sorted
            if (!swapped) break;
        }
        System.out.println("Students sorted by name.");
    }

    // Method to filter students by course
    static void filterStudentsByCourse(Student[] studentArray) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the course to filter by: ");
        String courseName = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i].courseEnrolled.equalsIgnoreCase(courseName)) {
				System.out.println("------------------------");
                studentArray[i].viewStudentDetails();
				System.out.println("------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No students found with that course.");
        }
    }

    // Method to view student report
    static void viewStudentReport(Student[] studentArray) {
        System.out.println("\nStudent Report:");
        for (Student student : studentArray) {
			System.out.println("------------------------");
            student.viewStudentDetails();
            System.out.println("------------------------");
        }
    }
}
