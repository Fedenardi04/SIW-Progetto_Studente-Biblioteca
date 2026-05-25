package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Autore {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String name;
private String surname;
private LocalDate birthDate;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSurname() {
	return surname;
}
public void setSurname(String surname) {
	this.surname = surname;
}
public LocalDate getBirthDate() {
	return birthDate;
}
public void setBirthDate(LocalDate birthDate) {
	this.birthDate = birthDate;
}
@Override
public int hashCode() {
	return Objects.hash(name, surname);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Autore other = (Autore) obj;
	return Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
}



}
