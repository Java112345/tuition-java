package main.java.com.ebtuition.model;

public class Booking {
    public Student student;
    public Lesson lesson;
    public String status = "booked";
    private String review;
    private int rating;

    public Booking(Student student2, Lesson lesson2) {
        this.student = student2;
        this.lesson = lesson2;
    }

    public void attend() {
        if ("booked".equals(this.status)) {
            this.status = "attended";
        }
    }

    public void addReview(String review, int rating) {
        if ("attended".equals(this.status)) {
            this.review = review;
            this.rating = rating;
        }
    }

    // Getters (optional) if you need to access private fields
    public String getReview() {
        return review;
    }

public Integer getRating() {
    return rating;
}

}
