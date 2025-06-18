package com.ebtuition.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.ebtuition.service.TuitionCentre;
import com.ebtuition.model.Student;
import com.ebtuition.model.Booking;
import com.ebtuition.model.Lesson;

public class MainGUI extends JFrame {
    private TuitionCentre centre = new TuitionCentre();
    private LocalDate startDate = LocalDate.of(2025, 1, 4);
    private JTextArea logArea = new JTextArea();
    private JTable reportTable = new JTable();
    private DefaultTableModel reportModel = new DefaultTableModel();

    public MainGUI() {
        setTitle("EB Tuition Centre Management");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Manager Panel
        JPanel managerPanel = new JPanel(new BorderLayout(10, 10));
        managerPanel.setBorder(BorderFactory.createTitledBorder("Manager Tasks"));

        JPanel managerButtons = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton reportBtn = new JButton("Generate Reports");
        JButton showStudentsBtn = new JButton("Show Students");
        JButton exitBtn = new JButton("Exit");
        managerButtons.add(reportBtn);
        managerButtons.add(showStudentsBtn);
        managerButtons.add(exitBtn);

        reportTable.setModel(reportModel);
        JScrollPane tableScroll = new JScrollPane(reportTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Reports (Students per Lesson + Avg Rating)"));

        managerPanel.add(managerButtons, BorderLayout.NORTH);
        managerPanel.add(tableScroll, BorderLayout.CENTER);

        // Student Panel
        JPanel studentPanel = new JPanel(new BorderLayout(10, 10));
        studentPanel.setBorder(BorderFactory.createTitledBorder("Student Console"));

        JPanel studentButtons = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton addStudentsBtn = new JButton("Add Students");
        JButton autoBookBtn = new JButton("Auto Book Lessons");
        studentButtons.add(addStudentsBtn);
        studentButtons.add(autoBookBtn);

        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Activity Log"));

        studentPanel.add(studentButtons, BorderLayout.NORTH);
        studentPanel.add(logScroll, BorderLayout.CENTER);

        // Tabs
        tabbedPane.add("Manager Dashboard", managerPanel);
        tabbedPane.add("Student Console", studentPanel);

        add(tabbedPane);

        // Actions
        addStudentsBtn.addActionListener(e -> {
            logArea.setText("");
            addStudents();
            JOptionPane.showMessageDialog(this, "15 Students added.");
        });

        autoBookBtn.addActionListener(e -> {
            logArea.setText("");
            autoBookLessons();
            JOptionPane.showMessageDialog(this, "Lessons auto-booked and reviewed.");
        });

        reportBtn.addActionListener(e -> {
            logArea.setText("");
            generateReports();
        });

        showStudentsBtn.addActionListener(e -> {
            showStudentRecords();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        // Initial lessons
        centre.createLessons(startDate);
    }

    private void addStudents() {
        for (int i = 1; i <= 15; i++) {
            Student s = new Student("Student" + i, (i % 2 == 0 ? "F" : "M"),
                    LocalDate.of(2012, (i % 12) + 1, (i % 28) + 1),
                    "Address " + i, "99999999" + i, i);
            centre.addStudent(s);
            log("Added: Student" + i);
        }
    }

    private void autoBookLessons() {
        Random rand = new Random();
        List<String> subjects = new ArrayList<>(centre.subjectPrices.keySet());
        List<Integer> studentIds = new ArrayList<>(centre.students.keySet());

        int studentIndex = 0;
        for (Lesson lesson : centre.getAllLessons()) {
            int studentId = studentIds.get(studentIndex % studentIds.size());
            Booking b = centre.bookLesson(studentId, lesson.getSubject(), lesson.getDate(), lesson.getTimeSlot());
            if (b != null) {
                centre.attendLesson(b);
                centre.reviewLesson(b, "Auto-filled: Good class", rand.nextInt(3) + 3);
                log("Booked + reviewed: Student " + studentId + " for " + lesson.getSubject());
            }
            studentIndex++;
        }

        for (int id : centre.students.keySet()) {
            for (int week = 0; week < 4; week++) {
                String subject = subjects.get(rand.nextInt(subjects.size()));
                String day = rand.nextBoolean() ? "Saturday" : "Sunday";
                String timeSlot = rand.nextBoolean() ? "Morning" : "Afternoon";
                LocalDate date = startDate.plusWeeks(week).plusDays(day.equals("Saturday") ? 0 : 1);
                Booking b = centre.bookLesson(id, subject, date, timeSlot);
                if (b != null) {
                    centre.attendLesson(b);
                    centre.reviewLesson(b, "Great lesson in " + subject, rand.nextInt(5) + 1);
                    log("Auto-booked: Student " + id + " => " + subject + " on " + date);
                }
            }
        }
    }

    private void generateReports() {
        // First table: Students per Lesson + Avg Rating
        reportModel.setRowCount(0);
        reportModel.setColumnCount(0);

        reportModel.addColumn("Date");
        reportModel.addColumn("Subject");
        reportModel.addColumn("TimeSlot");
        reportModel.addColumn("Students Count");
        reportModel.addColumn("Average Rating");

        for (Lesson lesson : centre.getAllLessons()) {
            int count = lesson.getBookings().size();
            double avgRating = lesson.getBookings().stream()
                    .filter(b -> b.getRating() > 0)
                    .mapToInt(Booking::getRating)
                    .average()
                    .orElse(0.0);

            reportModel.addRow(new Object[]{
                    lesson.getDate(),
                    lesson.getSubject(),
                    lesson.getTimeSlot(),
                    count,
                    String.format("%.2f", avgRating)
            });
        }

        // Show highest income subject also as a dialog:
        String highestIncome = getHighestIncomeSubject();
        JOptionPane.showMessageDialog(this, "Highest income subject: " + highestIncome);
    }

    private String getHighestIncomeSubject() {
        double maxIncome = -1;
        String topSubject = "";
        for (String subject : centre.subjectPrices.keySet()) {
            double income = centre.getIncomeForSubject(subject);
            if (income > maxIncome) {
                maxIncome = income;
                topSubject = subject;
            }
        }
        return topSubject + " (Income: " + maxIncome + ")";
    }

    private void showStudentRecords() {
        JFrame studentFrame = new JFrame("Student Records");
        studentFrame.setSize(700, 400);

        DefaultTableModel studentModel = new DefaultTableModel();
        JTable studentTable = new JTable(studentModel);

        studentModel.addColumn("ID");
        studentModel.addColumn("Name");
        studentModel.addColumn("Gender");
        studentModel.addColumn("Date of Birth");
        studentModel.addColumn("Address");
        studentModel.addColumn("Emergency Contact");

        for (Student s : centre.getAllStudents()) {
            studentModel.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getGender(),
                    s.getDob(),
                    s.getAddress(),
                    s.getEmergencyContact()
            });
        }

        JScrollPane scrollPane = new JScrollPane(studentTable);
        studentFrame.add(scrollPane);
        studentFrame.setVisible(true);
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }

    private void changeBooking() {
        String studentIdStr = JOptionPane.showInputDialog(this, "Enter Student ID:");
        String oldSubject = JOptionPane.showInputDialog(this, "Enter Current Subject:");
        String oldDateStr = JOptionPane.showInputDialog(this, "Enter Current Date (YYYY-MM-DD):");
        String oldTimeSlot = JOptionPane.showInputDialog(this, "Enter Current Time Slot (Morning/Afternoon):");

        String newDateStr = JOptionPane.showInputDialog(this, "Enter New Date (YYYY-MM-DD):");
        String newTimeSlot = JOptionPane.showInputDialog(this, "Enter New Time Slot (Morning/Afternoon):");

        try {
            int studentId = Integer.parseInt(studentIdStr);
            LocalDate oldDate = LocalDate.parse(oldDateStr);
            LocalDate newDate = LocalDate.parse(newDateStr);

            // Find the existing booking
            Booking oldBooking = null;
            for (Booking booking : centre.getAllBookings()) {
                if (booking.getStudent().getId() == studentId &&
                        booking.getLesson().getSubject().equals(oldSubject) &&
                        booking.getLesson().getDate().equals(oldDate) &&
                        booking.getLesson().getTimeSlot().equalsIgnoreCase(oldTimeSlot) &&
                        booking.getStatus().equals("booked")) {

                    oldBooking = booking;
                    break;
                }
            }

            if (oldBooking != null) {
                Booking newBooking = centre.changeBooking(oldBooking, newDate, newTimeSlot);
                if (newBooking != null) {
                    log("Booking changed successfully! Student " + studentId + " to " + newDate + " " + newTimeSlot);
                } else {
                    log("Failed to change booking (maybe no available slot).");
                }
            } else {
                log("Original booking not found.");
            }

        } catch (Exception e) {
            log("Error changing booking: " + e.getMessage());
        }
    }
}
