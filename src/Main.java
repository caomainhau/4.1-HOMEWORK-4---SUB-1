import java.io.*;
import java.util.*;

class Student {
    String studentID;
    String fullName;
    String gender;
    double pythonScore;
    double javaScore;
    double averageScore;
    String result;

    public Student(String studentID, String fullName, String gender, double pythonScore, double javaScore) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.gender = gender;
        this.pythonScore = pythonScore;
        this.javaScore = javaScore;
        this.averageScore = (javaScore * 2 + pythonScore) / 3;
        setResult();
    }

    private void setResult() {
        if (averageScore >= 5) {
            result = "Đậu";
        } else if (averageScore < 5 && gender.equalsIgnoreCase("Nam")) {
            result = "Trượt";
        } else if (averageScore < 5 && gender.equalsIgnoreCase("Nữ")) {
            result = "Đậu";
        }
    }

    public String toString() {
        return String.format("%-10s %-20s %-5s %-10.1f %-10.1f %-10.1f %-5s", studentID, fullName, gender, pythonScore, javaScore, averageScore, result);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        // Nhập và ghi ra file danh sách học viên
        try {
            System.out.print("Nhập số lượng học viên: ");
            int n = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống

            PrintWriter writer = new PrintWriter("input.txt");
            for (int i = 0; i < n; i++) {
                System.out.println("Nhập thông tin học viên thứ " + (i + 1) + ":");
                System.out.print("Mã sinh viên: ");
                String studentID = scanner.nextLine();
                System.out.print("Họ và tên: ");
                String fullName = scanner.nextLine();
                System.out.print("Giới tính: ");
                String gender = scanner.nextLine();
                System.out.print("Điểm Python: ");
                double pythonScore = scanner.nextDouble();
                System.out.print("Điểm Java: ");
                double javaScore = scanner.nextDouble();
                scanner.nextLine(); // Đọc bỏ dòng trống

                Student student = new Student(studentID, fullName, gender, pythonScore, javaScore);
                students.add(student);

                writer.println(studentID + "," + fullName + "," + gender + "," + pythonScore + "," + javaScore);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file.");
            e.printStackTrace();
        }

        // Đọc dữ liệu từ file và thực hiện các công việc sau
        try {
            Scanner fileScanner = new Scanner(new File("input.txt"));
            PrintWriter writer = new PrintWriter("output.txt");

            System.out.println("\nDanh sách học viên:");
            System.out.println("Mã SV    Họ và Tên           Giới tính Điểm Python Điểm Java  Điểm TB   Kết quả");
            writer.println("Mã SV,Họ và Tên,Giới tính,Điểm Python,Điểm Java,Điểm TB,Kết quả");
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                Student student = new Student(data[0], data[1], data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]));
                System.out.println(student);
                writer.println(student);
            }
            fileScanner.close();
            writer.close();

            // Sắp xếp học viên theo thứ tự giảm dần theo điểm trung bình và ghi ra file
            Collections.sort(students, Comparator.comparingDouble((Student s) -> s.averageScore).reversed());
            writer = new PrintWriter("output.txt");
            writer.println("\nDanh sách học viên sau khi sắp xếp:");
            writer.println("Mã SV,Họ và Tên,Giới tính,Điểm Python,Điểm Java,Điểm TB,Kết quả");
            for (Student student : students) {
                writer.println(student);
            }
            writer.close();

            // Nhập vào họ tên học viên cần tìm và tìm kiếm trong danh sách học viên
            System.out.print("\nNhập họ tên học viên cần tìm: ");
            String searchName = scanner.nextLine();
            boolean found = false;
            System.out.println("Kết quả tìm kiếm:");
            for (Student student : students) {
                if (student.fullName.equalsIgnoreCase(searchName)) {
                    System.out.println(student);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy học viên có họ tên " + searchName);
            }

            // Hiển thị thông tin những bạn đã đậu
            System.out.println("\nThông tin những bạn đã đậu:");
            for (Student student : students) {
                if (student.result.equals("Đậu")) {
                    System.out.println(student);
                }
            }

            // Hiển thị thông tin những bạn đã trượt
            System.out.println("\nThông tin những bạn đã trượt:");
            for (Student student : students) {
                if (student.result.equals("Trượt")) {
                    System.out.println(student);
                }
            }

            // Hiển thị những bạn có điểm trung bình lớn hơn 8
            System.out.println("\nThông tin những bạn có điểm trung bình lớn hơn 8:");
            for (Student student : students) {
                if (student.averageScore > 8) {
                    System.out.println(student);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file input.txt");
            e.printStackTrace();
        }
    }
}
