package gp.objects;

public class Author {

	String FirstName = null;
	String MiddleName = null;
	String LastName = null;
	String FirstNameInitial = null;
	String MiddleNameInitial = null;
	String LastNameInitial = null;
	String AuthorName = null;

	public Author() {
		super();
		FirstName = "";
		MiddleName = "";
		LastName = "";
	}

	public Author(String firstName, String middleName, String lastName) {
		super();
		FirstName = firstName;
		MiddleName = middleName;
		LastName = lastName;
		FirstNameInitial = String.valueOf(firstName.charAt(0));
		MiddleNameInitial = String.valueOf(middleName.charAt(0));
		LastNameInitial = String.valueOf(lastName.charAt(0));
		setAuthorName();
	}

	public void setAuthorName() {
		AuthorName = getFirstName() + getMiddleName() + getLastName();
	}
	
	
	public String getAuthorName() {
		return AuthorName;
	}

	public void setAuthorName(String authorName) {
		AuthorName = authorName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
		setFirstNameInitial(firstName);

	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
		setMiddleNameInitial(middleName);
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
		setLastNameInitial(lastName);
	}

	public String getFirstNameInitial() {
		return FirstNameInitial;
	}

	public void setFirstNameInitial(String firstName) {
		FirstNameInitial = String.valueOf(firstName.charAt(0));
	}

	public String getMiddleNameInitial() {
		return MiddleNameInitial;
	}

	public void setMiddleNameInitial(String middleName) {
		MiddleNameInitial = String.valueOf(middleName.charAt(0));
	}

	public String getLastNameInitial() {
		return LastNameInitial;
	}

	public void setLastNameInitial(String lastName) {
		LastNameInitial = String.valueOf(lastName.charAt(0));
	}

	public String getFirstAndLastName() {
		String output = getFirstName() + " " + getLastName();
		
		output = output.replaceAll("\\s+", " ");
		
		return output;
		
		
	}

}
