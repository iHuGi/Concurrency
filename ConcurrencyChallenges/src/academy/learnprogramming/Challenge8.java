package academy.learnprogramming;

public class Challenge8 {

    // Solved? Seems to work as least.

    public static void main(String[] args) {
        final Tutor tutor = new Tutor();
        final Student student = new Student(tutor);
        tutor.setStudent(student);

        Thread tutorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                tutor.studyTime();
            }
        });

        Thread studentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                student.handInAssignment();
            }
        });

        tutorThread.start();
        studentThread.start();
    }
}

class Tutor {
    private Student student;

    public synchronized void setStudent(Student student) {
        this.student = student;
    }

    public synchronized void studyTime() {

        synchronized (this) {
            System.out.println("Tutor has arrived");
            try {
                // wait for student to arrive and hand in assignment
                this.wait();
            } catch (InterruptedException e) {

            }
            student.startStudy();
            System.out.println("Tutor is studying with student");
        }
    }

    public synchronized void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class Student {

    private final Tutor tutor;

    Student(Tutor tutor) {
        this.tutor = tutor;
    }

    public synchronized void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public synchronized void handInAssignment() {
        synchronized (tutor) {
            tutor.getProgressReport();
            synchronized (this) {
                System.out.println("Student handed in assignment");
                tutor.notifyAll();
            }
        }
    }
}