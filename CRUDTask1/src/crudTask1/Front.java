package crudTask1;

import java.io.IOException;

public class Front {

	public static void main(String[] args) throws IOException {

		var p1 = new Person("Igor", "I", 28);
		var p2 = new Person("Egor", "E", 22);
		var p3 = new Person("Nik", "N", 29);
		var p4 = new Person("Den", "D", 25);
	//	List<Person> l = List.of(p1, p2, p3, p4);
		WorkPath c = WorkPath.create("---");
		FileManipulator fm = FileManipulator.create(c);
		var o = fm.serch("T*st");
		o.forEach(System.out::println);
	}

}
