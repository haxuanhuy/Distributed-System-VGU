package main;

import java.io.Serializable;
import model.Student;

public final class Message implements Serializable {
	private Student student;

	Message(Student student) {
		this.student = student;
	}
	
	public Student getStudent() {
		return student;
	}
}
