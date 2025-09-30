import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class ELearningPlatform extends JFrame {
    private JTextField nameField, rollField;
    private JButton loginButton;
    private JPanel panel;
    public static String studentName;
    public static String rollNumber;

    public ELearningPlatform() {
	
        setTitle("E-Learning Platform - Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(new Color(240, 240, 255));

        JLabel loginLabel = new JLabel("Login / Register", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setForeground(new Color(60, 90, 180));
        panel.add(loginLabel);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(240, 240, 255));
        nameField = new JTextField(10);
        rollField = new JTextField(10);
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Roll No: "));
        inputPanel.add(rollField);
        panel.add(inputPanel);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(255, 69, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> openDashboard());
        panel.add(loginButton);

        setLayout(new GridBagLayout());
        add(panel);
        setVisible(true);
    }

    private void openDashboard() {
    ELearningPlatform.studentName = nameField.getText(); 
    ELearningPlatform.rollNumber = rollField.getText(); 
    new ELearningDashboard(ELearningPlatform.studentName, ELearningPlatform.rollNumber);
    dispose();
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ELearningPlatform());
    }
}

class ELearningDashboard extends JFrame {
    private static ArrayList<Course> courses = new ArrayList<>();
    private static int score = -1; 
    public static boolean testTaken = false;
    private static int progress = 0;


    public ELearningDashboard(String name, String rollNo) {
        setTitle("E-Learning Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBackground(new Color(220, 235, 255));

        String[] options = {"Add Course", "See Progress", "View Videos", "Take Test", "See Result", "View History"};
        String[] imagePaths = {"add_course.png", "see_progress.png", "view_videos.png", "take_test.png", "result.png", "history.png"};

        Dimension buttonSize = new Dimension(180, 150);

        for (int i = 0; i < options.length; i++) {
            ImageIcon icon = new ImageIcon(new ImageIcon(imagePaths[i]).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            JButton button = new JButton(options[i], icon);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(255, 165, 0));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setPreferredSize(buttonSize);
            
            if (options[i].equals("Add Course")) {
                button.addActionListener(e -> openAddCourseWindow());
            }
            if (options[i].equals("View Videos")) {
                button.addActionListener(e -> openVideoFolder());
            }
            if (options[i].equals("Take Test")) {
                button.addActionListener(e -> openTestWindow());
            }
            if (options[i].equals("View History")) {
                button.addActionListener(e -> viewHistory(name, rollNo));
            }
            if (options[i].equals("See Result")) {
                button.addActionListener(e -> displayResult());
            }
	    if (options[i].equals("See Progress")) {
                button.addActionListener(e -> showProgress());
            }
           


            panel.add(button);
        }

        setLayout(new GridBagLayout());
        add(panel);
        setVisible(true);
    }

    private void openAddCourseWindow() {
        JFrame addCourseFrame = new JFrame("Add Course");
        addCourseFrame.setSize(300, 200);
        addCourseFrame.setLocationRelativeTo(null);
        addCourseFrame.setLayout(new GridLayout(3, 2, 5, 5));

        String[] courseOptions = {"Java OOPS", "Nodejs", "Expressjs", "SQL", "Reactjs"};
        JComboBox<String> courseDropdown = new JComboBox<>(courseOptions);
        JTextField courseDurationField = new JTextField();
        JButton submitButton = new JButton("Submit");

        addCourseFrame.add(new JLabel("Course Name: "));
        addCourseFrame.add(courseDropdown);
        addCourseFrame.add(new JLabel("Duration: "));
        addCourseFrame.add(courseDurationField);
        addCourseFrame.add(new JLabel());
        addCourseFrame.add(submitButton);

        submitButton.addActionListener(e -> {
            String selectedCourse = (String) courseDropdown.getSelectedItem();
            String courseDuration = courseDurationField.getText();
            if (!courseDuration.isEmpty()) {
                courses.add(new Course(selectedCourse, courseDuration));
                JOptionPane.showMessageDialog(addCourseFrame, "Course Added Successfully!");
                addCourseFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(addCourseFrame, "Please enter the duration.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addCourseFrame.setVisible(true);
	updateProgress(10);
    }

    private void openVideoFolder() {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No course added yet!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Course lastAddedCourse = courses.get(courses.size() - 1);
        String courseName = lastAddedCourse.getName();

        String folderPath = "";

        switch (courseName) {
            case "Java OOPS":
                folderPath = "C:\\Users\\DELL\\Downloads\\Day 33 JS OOPs";
                break;
            case "Nodejs":
                folderPath = "C:\\Users\\DELL\\Downloads\\Day 30 Nodejs";
                break;
            case "Expressjs":
                folderPath = "C:\\Users\\DELL\\Downloads\\Day 31 expressjs";
                break;
            case "SQL":
                folderPath = "C:\\Users\\DELL\\Downloads\\Day 35 SQL\\40. Starting with SQL";
                break;
            case "Reactjs":
                folderPath = "C:\\Users\\DELL\\Downloads\\Day 57 React";
                break;
            default:
                JOptionPane.showMessageDialog(this, "No folder available for this course.");
                return;
        }

        File videoFolder = new File(folderPath);
        if (videoFolder.exists() && videoFolder.isDirectory()) {
            try {
                Desktop.getDesktop().open(videoFolder);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Unable to open folder.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Folder does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }

	updateProgress(10);
    }

    private void openTestWindow() {
        JFrame testFrame = new JFrame("Java OOPs Test");
        testFrame.setSize(500, 600);
        testFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] questions = {
            "1. What is encapsulation in Java?",
            "2. What is the purpose of the 'this' keyword?",
            "3. What is method overloading?",
            "4. What is inheritance?",
            "5. What is an abstract class?",
            "6. What is an interface?",
            "7. What is polymorphism?",
            "8. What is the difference between final, finally, and finalize?",
            "9. What is a constructor?",
            "10. What is the difference between static and non-static methods?"
        };

        String[][] options = {
            {"Hiding data", "Accessing data", "Copying data", "Deleting data"},
            {"Refers to the current object", "Refers to a superclass", "Refers to a method", "Refers to a variable"},
            {"Multiple methods with the same name but different parameters", "Inheritance", "Using interfaces", "Runtime polymorphism"},
            {"Acquiring properties of another class", "Multiple methods", "Overloading", "None of these"},
            {"A class that cannot be instantiated", "A subclass", "A final class", "A class with no methods"},
            {"A blueprint for classes", "A subclass", "A final class", "A method"},
            {"Ability of an object to take many forms", "Method overloading", "Method overriding", "Inheritance"},
            {"final: Prevents inheritance, finally: Executes code, finalize: Garbage collection", "All are the same", "Used in loops", "Used in interfaces"},
            {"A special method used to initialize objects", "A destructor", "A static method", "An interface method"},
            {"Static methods belong to the class, non-static methods belong to an object", "Both are the same", "Only static methods are allowed", "Only non-static methods are allowed"}
        };

        ButtonGroup[] groups = new ButtonGroup[10];
        JRadioButton[][] radioButtons = new JRadioButton[10][4];

        for (int i = 0; i < 10; i++) {
            JLabel questionLabel = new JLabel(questions[i]);
            panel.add(questionLabel);
            panel.add(Box.createVerticalStrut(5));

            groups[i] = new ButtonGroup();
            for (int j = 0; j < 4; j++) {
                radioButtons[i][j] = new JRadioButton(options[i][j]);
                groups[i].add(radioButtons[i][j]);
                panel.add(radioButtons[i][j]);
                panel.add(Box.createVerticalStrut(5));
            }
            panel.add(Box.createVerticalStrut(15));
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
    int score = 0;
    int[] correctAnswers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    for (int i = 0; i < 10; i++) {
        if (radioButtons[i][correctAnswers[i]].isSelected()) {
            score++;
        }
    }

    ELearningDashboard.score = score;
    ELearningDashboard.testTaken = true; // Mark test as taken
    JOptionPane.showMessageDialog(testFrame, "Your score is: " + score + "/10"); 
    updateProgress(10);
});



        panel.add(submitButton);
        testFrame.add(new JScrollPane(panel));
        testFrame.setVisible(true);
    }

    private void displayResult() {
    if (ELearningDashboard.testTaken) {
        JOptionPane.showMessageDialog(this, "Name: " + ELearningPlatform.studentName + 
            "\nRoll No: " + ELearningPlatform.rollNumber + "\nScore: " + score + "/10");
    } else {
        JOptionPane.showMessageDialog(this, "Test not taken yet!");
    }
}




    private void viewHistory(String name, String rollNo) {
        StringBuilder history = new StringBuilder("Student: " + name + " (Roll No: " + rollNo + ")\n\nCourses Enrolled:\n");
        for (Course course : courses) {
            history.append("- ").append(course.getName()).append(" (Duration: ").append(course.getDuration()).append(")\n");
        }
        JOptionPane.showMessageDialog(this, history.toString(), "Course History", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateProgress(int increment) {
    if (!testTaken) {
        progress = Math.min(progress + increment, 90); // Cap at 90% before test
    } else {
        progress = Math.min(progress + increment, 100); // 100% after test
    }
    }

    private void showProgress() {
    JProgressBar progressBar = new JProgressBar(0, 100);
    progressBar.setValue(progress);
    progressBar.setStringPainted(true);

    String message = "Name: " + ELearningPlatform.studentName +
                    "\nRoll No: " + ELearningPlatform.rollNumber +
                    "\nOverall Progress: " + progress + "%";

    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(new JLabel(message));
    panel.add(progressBar);

    JOptionPane.showMessageDialog(this, panel, "Your Progress", JOptionPane.INFORMATION_MESSAGE);
    }
}

class Course {
    private String name;
    private String duration;

    public Course(String name, String duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }
}


