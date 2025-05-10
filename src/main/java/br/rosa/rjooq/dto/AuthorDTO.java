package br.rosa.rjooq.dto;

public class AuthorDTO {

	private Integer id;
	private String firstName;
	private String lastName;

	public static AuthorDTO newInstance(Integer id, String firstName, String lastName) {
		AuthorDTO dto = new AuthorDTO();
		dto.setId(id);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		return dto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return id + " - " + firstName + " - " + lastName;
	}
}
